import { Component, OnInit,Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../service/course.service';
import {MatDialog,MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Archive } from '../model/archive.model';
import { AuthService } from '../service/auth.service';
import { ArchiveService } from '../service/archive.service';

@Component({
  selector: 'app-course-view',
  templateUrl: './course-view.component.html',
  styleUrls: ['./course-view.component.css']
})
export class CourseViewComponent implements OnInit {

  courses!:Course[];
  username!:string;  

  constructor(private route:ActivatedRoute, private courseService:CourseService,
     private router:Router, private dialog:MatDialog, private authService:AuthService,
     private archiveService:ArchiveService) { }

  ngOnInit(): void {
    this.username = this.authService.getSignedinUser();
    this.getCourses()
  }

  viewCourse(course:Course){
    const dialogRef = this.dialog.open(AppCourseViewDialog,{
      width: '600px',
      height:'300px',
      data: course
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
    
  }

  participateCourse(course:Course){
    const username = this.authService.getSignedinUser();

    let today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    
    let today3 = yyyy + "-" + mm + "-" + dd;

    const archive:Archive ={courseName:course.courseName,userName:username,dateOfEntry:today3};

    this.archiveService.addArchive(archive).subscribe(data=>{
      alert(data);
    });

    console.log(archive);
    let today2 = new Date().toLocaleDateString()
    console.log(today2)

  }

  getCourses(){
      this.courseService.getCourses(this.username).subscribe(data=>{
        console.log(data);
        this.courses = data;
      });
  }
}

@Component({
  selector: 'app-course-view-dialog',
  templateUrl: './app-course-view-dialog.html',
})
export class AppCourseViewDialog {
  constructor(
    public dialogRef: MatDialogRef<AppCourseViewDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Course,
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
