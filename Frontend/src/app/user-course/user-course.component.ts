import { Component, Inject, OnInit } from '@angular/core';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Archive } from '../model/archive.model';
import { Course } from '../model/course.model';
import { ArchiveService } from '../service/archive.service';
import { AuthService } from '../service/auth.service';
import { CourseService } from '../service/course.service';
import { CourseTopicService } from '../service/courseTopic.service';

@Component({
  selector: 'app-user-course',
  templateUrl: './user-course.component.html',
  styleUrls: ['./user-course.component.css'],
})
export class UserCourseComponent implements OnInit {
  courses!: Course[];
  username: string = '';
  complete!: Map<string, boolean>;
  listComplete: boolean[] = [];
  archive!: Archive;
  archives: Archive[] = [];
  loaded: boolean = false;

  constructor(
    private authService: AuthService,
    private courseService: CourseService,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private archiveService: ArchiveService,
    private courseTopicService: CourseTopicService
  ) {}

  ngOnInit(): void {
    this.username = this.authService.getSignedinUser();

    this.getCourse2();
  }

  deduct(course: Course) {
    this.archiveService
      .deduct(course.courseName, this.username)
      .subscribe((data) => {
        console.log(data);

        this.getCourse2();
      });
  }

  async getCourse2() {
    console.log('loaded: ' + this.loaded);
    this.courseService
      .getCourseByUserName(this.username)
      .subscribe(async (data) => {
        console.log(data);

        this.courses = data;

        for (let course of this.courses) {
          let arch: Archive = new Archive();

          arch = <Archive>await this.getArc(course);
          this.archives.push(arch);
        }

        this.loaded = true;
        console.log('loaded: ' + this.loaded);
      });
  }

  getArc(course: Course): Promise<any> {
    return this.archiveService
      .getArchiveByCourseNameAndUserName(course.courseName, this.username)
      .toPromise();
  }

  viewPresence(id: number) {
    this.router.navigate(['view-user-presence', id]);
  }

  viewFinalMark(archiveId: number) {
    this.archive = this.archives[archiveId];

    const dialogRef = this.dialog.open(AppUserCourseDialog, {
      width: '600px',
      height: '250px',
      data: this.archive,
    });
  }
}

@Component({
  selector: 'app-user-course-dialog',
  templateUrl: './app-user-course-dialog.html',
})
export class AppUserCourseDialog {
  constructor(
    public dialogRef: MatDialogRef<AppUserCourseDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Archive
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
