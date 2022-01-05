import { Component, OnInit,Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { User } from '../model/user.model';
import { CourseService } from '../service/course.service';
import { UserService } from '../service/user.service';
import {MatDialog,MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TopicService } from '../service/topic.service';
import { CourseTopicService } from '../service/courseTopic.service';
import { CourseTopic } from '../model/courseTopic.model';

@Component({
  selector: 'app-course-info',
  templateUrl: './course-info.component.html',
  styleUrls: ['./course-info.component.css']
})
export class CourseInfoComponent implements OnInit {

  id!:number;
  course:Course = new Course();
  users:User[] = [];
  topics:string[] = [];
  complete:boolean = true;
  loaded: boolean = false;
  courseTopicList:CourseTopic[] = [];

  constructor(private route:ActivatedRoute, private courseService:CourseService,private router: Router,
    private userService:UserService,private dialog:MatDialog,
    private courseTopicService:CourseTopicService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    console.log(this.id);
    
    console.log("loaded: " + this.loaded);

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

      this.getCourseTopic2();

    });
  }

  getCourseTopic2(){
    this.courseTopicService.getCourseTopicByCourseName(this.course.courseName).subscribe(data=>{
      console.log(data);
      
      this.courseTopicList = data;

      for(let courseTopic of this.courseTopicList){
        if(courseTopic.dateOfStudy! == 'no date'){
          this.complete = false;
          break;
        }
      }
      this.loaded = true;
    });
  }

  userInfo(user:User){
    const dialogRef = this.dialog.open(AppCourseInfoDialog,{
      width: '600px',
      height:'200px',
      data: user

    });
  }

  teacherCourse(){
    this.router.navigateByUrl("teacher-course");
  }

  markPresence(){
    this.router.navigate(['mark-presence',this.id]);
  }

  watchAttendance(){
    this.router.navigate(['watch-attendance',this.id]);
  }

  finalMark(){
    this.router.navigate(['rate',this.id]);
  }
}

@Component({
  selector: 'app-course-info-dialog',
  templateUrl: './app-course-info-dialog.html'
})
export class AppCourseInfoDialog {
  constructor(
    public dialogRef: MatDialogRef<AppCourseInfoDialog>,
    @Inject(MAT_DIALOG_DATA) public data: User,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
