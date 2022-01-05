import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { Presence } from '../model/presence.model';
import { AuthService } from '../service/auth.service';
import { CourseService } from '../service/course.service';
import { PresenceService } from '../service/presence.service';
import { TopicService } from '../service/topic.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-view-user-presence',
  templateUrl: './view-user-presence.component.html',
  styleUrls: ['./view-user-presence.component.css']
})
export class ViewUserPresenceComponent implements OnInit {

  id!:number;
  userName!:string;
  course:Course = new Course();
  presences:Presence[]=[];

  constructor(private route:ActivatedRoute, private courseService:CourseService,private router: Router,
    private topicService:TopicService,private authService:AuthService, private presenceService:PresenceService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    console.log(this.id);

    this.userName = this.authService.getSignedinUser(); 

    this.getCourse(this.id);
  }

  getCourse(id:number){

    this.courseService.getCourseById(id).subscribe(data=>{
      console.log("course");
      console.log(data);
      this.course = data as Course;

      this.getUserPresence();
    });
  }

  getUserPresence(){

    this.presenceService.getPresenceByCourseNameAndUserName(this.course.courseName,this.userName).subscribe(data=>{
      console.log(data);
      this.presences = data;
    });
  }

  userCourse(){
    this.router.navigateByUrl("user-course");
  }

}
