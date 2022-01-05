import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { Presence } from '../model/presence.model';
import { User } from '../model/user.model';
import { CourseService } from '../service/course.service';
import { CourseTopicService } from '../service/courseTopic.service';
import { PresenceService } from '../service/presence.service';
import { TopicService } from '../service/topic.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-watch-attendance',
  templateUrl: './watch-attendance.component.html',
  styleUrls: ['./watch-attendance.component.css']
})
export class WatchAttendanceComponent implements OnInit {

  course:Course = new Course();
  id!:number;
  loadTopic:string[] = [];
  users:User[] = [];
  presences:Presence[]=[];
  userInfo:string[] = [];
  selectTopic:string = "Выберите тему занятия";
  dateOfStudy:string='';

  constructor(private route:ActivatedRoute, private courseService:CourseService,private router: Router,
    private userService:UserService,private topicService:TopicService,
    private presenceService:PresenceService,private courseTopicService:CourseTopicService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    console.log(this.id);
    
    this.getCourse(this.id);
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

      
      this.getCourseTopic()
    });
  }

  getCourseTopic(){
    this.topicService.getTopicByCourseName(this.course.courseName).subscribe(data=>{
      console.log(data);

      this.loadTopic = data;

    });
  }

  onChangeTopic(selectTop: string){
    this.presences = [] as Presence[];
    this.dateOfStudy = '';
    if(selectTop!="Выберите тему занятия"){

      this.courseTopicService.getDateOfStudyByCourseNameAndTopicName(this.course.courseName, selectTop).subscribe(data=>{
        console.log(data);
        this.dateOfStudy = data;
      
        this.getPresence(selectTop);
      });
    }
  }

  getPresence(topic:string){
    this.presenceService.getPresenceByCourseNameAndTopicName(this.course.courseName,topic).subscribe(data=>{
      console.log(data);
      
      this.presences = data;
    })
  }


  onClick(){
    this.router.navigate(['course-info',this.id]);
  }
}
