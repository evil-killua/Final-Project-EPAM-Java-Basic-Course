import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Archive } from '../model/archive.model';
import { Course } from '../model/course.model';
import { ArchiveService } from '../service/archive.service';
import { AuthService } from '../service/auth.service';
import { CourseService } from '../service/course.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-rate',
  templateUrl: './rate.component.html',
  styleUrls: ['./rate.component.css']
})
export class RateComponent implements OnInit {

  id!:number;
  course:Course = new Course();
  userName!:string;
  archiveList:Archive[]=[];
  error: string = '';
	success: string = '';
  flag:boolean=false;

  constructor(private route:ActivatedRoute, private courseService:CourseService,private router: Router,
    private authService:AuthService,private archiveService:ArchiveService) { }

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

      this.getArchive();
    });
  }

  getArchive(){
    this.archiveService.getArchiveByCourseName(this.course.courseName).subscribe(data=>{
      console.log(data);
      
      this.archiveList = data;
    });
  }

  save(){
    this.success='';
    this.error='';
    for(let arch of this.archiveList){
      
      console.log("gr: " + arch.graduationGrade);
      
      if(arch.graduationGrade! == null){
        this.error = 'This field is required.';

        break;
      }else if(arch.graduationGrade!>10 || arch.graduationGrade!<1){
        this.error='marks must be from 1 to 10';

        break;
      }
    }

    if(!this.error){
      
      this.archiveService.updateArchive(this.archiveList).subscribe(data=>{
        console.log(data);
        
      });
      this.success='grades have been successfully submitted';
    }
  }

  onClick(){
    this.router.navigate(['course-info',this.id]);
  }

}
