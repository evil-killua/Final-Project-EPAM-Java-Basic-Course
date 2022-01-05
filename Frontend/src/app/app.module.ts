import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { HttpInterceptorService } from './service/httpInterceptor.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { FormsModule } from '@angular/forms';
import { CourseViewComponent } from './course-view/course-view.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { UserCourseComponent } from './user-course/user-course.component';
import { TeacherCourseComponent } from './teacher-course/teacher-course.component';
import { CourseInfoComponent } from './course-info/course-info.component';
import { CreateCourseComponent } from './create-course/create-course.component';
import { MarkPresenceComponent } from './mark-presence/mark-presence.component';
import { WatchAttendanceComponent } from './watch-attendance/watch-attendance.component';
import { ViewUserPresenceComponent } from './view-user-presence/view-user-presence.component';
import { RateComponent } from './rate/rate.component';
import { UserListComponent } from './user-list/user-list.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { EmailValidatorDirective } from './signup/email-validator.directive';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SigninComponent,
    SignupComponent,
    CourseViewComponent,
    UserCourseComponent,
    TeacherCourseComponent,
    CourseInfoComponent,
    CreateCourseComponent,
    MarkPresenceComponent,
    WatchAttendanceComponent,
    ViewUserPresenceComponent,
    RateComponent,
    UserListComponent,
    UpdateUserComponent,
    EmailValidatorDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
