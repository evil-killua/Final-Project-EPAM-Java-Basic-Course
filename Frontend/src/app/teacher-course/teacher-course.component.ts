import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Course } from '../model/course.model';
import { ArchiveService } from '../service/archive.service';
import { AuthService } from '../service/auth.service';
import { CourseService } from '../service/course.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-teacher-course',
  templateUrl: './teacher-course.component.html',
  styleUrls: ['./teacher-course.component.css']
})
export class TeacherCourseComponent implements OnInit {

  courses!:Course[];

  constructor(private authService:AuthService,private archiveService:ArchiveService,
    private courseService:CourseService,private userService:UserService,private router:Router) { }

  ngOnInit(): void {

    const username = this.authService.getSignedinUser();

    this.courseService.getTeacherCourseByUserName(username).subscribe(data=>{
      console.log(data);
      this.courses = data;
    })
  }

  courseInfo(id:number){
    this.router.navigate(['course-info',id]);
  }

}
