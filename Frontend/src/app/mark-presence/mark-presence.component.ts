import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseTopic } from '../model/courseTopic.model';
import { Presence } from '../model/presence.model';
import { User } from '../model/user.model';
import { CourseService } from '../service/course.service';
import { CourseTopicService } from '../service/courseTopic.service';
import { PresenceService } from '../service/presence.service';
import { TopicService } from '../service/topic.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-mark-presence',
  templateUrl: './mark-presence.component.html',
  styleUrls: ['./mark-presence.component.css']
})
export class MarkPresenceComponent implements OnInit {

  course:Course = new Course();
  id!:number;
  loadTopic:string[] = [];
  users:User[] = [];
  presences:Presence[]=[];

  selectTopic:string = "Выберите тему занятия";
  userInfo:string[] = [];
  message:string='';
  selectDate!:Date;

  constructor(private route:ActivatedRoute, private courseService:CourseService,private router: Router,
    private userService:UserService,private dialog:MatDialog,private topicService:TopicService,
    private presenceService:PresenceService,private courseTopicService:CourseTopicService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    console.log(this.id);
    
    this.getCourse(this.id);
  }

  onClick(){
    this.router.navigate(['course-info',this.id]);
  }

  onChangeDate(date:Date){
    console.log(date);
    this.selectDate = date;
  }

  save(){
    if(this.presences.length>0 && this.selectDate!=null){
      this.presenceService.createPresence(this.presences).subscribe(data=>{
        console.log(data);

        let courseTopic:CourseTopic = {courseName:this.course.courseName,topicName:this.selectTopic,
          dateOfStudy!:this.selectDate.toString()}
        this.courseTopicService.updateCourseTopic(courseTopic).subscribe(data=>{
          console.log(data);
          
        })

        this.message = data;
      });
    }
  }

  onChangeTopic(selectTop: string){
    this.presences = [] as Presence[];
    if(selectTop!="Выберите тему занятия"){
      for(let user of this.users){
        let pres:Presence ={courseName:this.course.courseName,topicName:selectTop,
                  userName:user.firstName + " " + user.lastName,userPresence:true};
          this.presences.push(pres);
      }
    }
  }

  getCourse(id:number){

    this.courseService.getCourseById(id).subscribe(data=>{
      console.log("course");
      console.log(data);
      this.course = data as Course;

      this.getCourseUsers();
    });
  }

  getCourseUsers(){
    this.userService.getUserByCourseName(this.course.courseName).subscribe(data=>{
      console.log("users");
      console.log(data);
      this.users = data;

      for(let user of this.users){
        let info = user.firstName + " " + user.lastName;
        console.log(info);
        this.userInfo.push(info);
      }

      this.getCourseTopic();
    });
  }

  getCourseTopic(){
    this.topicService.getNotPassedCourseTopicByCourseId(this.id).subscribe(data=>{
      console.log(data);
      
      this.loadTopic = data;
    });
  }

  markPresence(pres:Presence){
    console.log(pres.userPresence);
    pres.userPresence = !pres.userPresence;
    console.log(pres.userPresence);
  }
}
