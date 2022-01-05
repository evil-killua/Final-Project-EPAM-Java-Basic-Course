import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseInfoComponent } from './course-info/course-info.component';
import { CourseViewComponent } from './course-view/course-view.component';
import { CreateCourseComponent } from './create-course/create-course.component';
import { HomeComponent } from './home/home.component';
import { MarkPresenceComponent } from './mark-presence/mark-presence.component';
import { RateComponent } from './rate/rate.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { TeacherCourseComponent } from './teacher-course/teacher-course.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UserCourseComponent } from './user-course/user-course.component';
import { UserListComponent } from './user-list/user-list.component';
import { ViewUserPresenceComponent } from './view-user-presence/view-user-presence.component';
import { WatchAttendanceComponent } from './watch-attendance/watch-attendance.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
	{ path: 'home',component:HomeComponent},
	{ path: 'signin', component: SigninComponent },
	{ path: 'signup', component: SignupComponent },
  { path: 'course-view',component:CourseViewComponent},
  { path: 'user-course',component:UserCourseComponent},
  { path: 'teacher-course',component:TeacherCourseComponent},
  { path: 'course-info/:id',component:CourseInfoComponent},
  { path: 'create-course', component:CreateCourseComponent},
  { path: 'mark-presence/:id',component:MarkPresenceComponent},
  { path: 'watch-attendance/:id',component:WatchAttendanceComponent},
  { path: 'view-user-presence/:id',component:ViewUserPresenceComponent},
  { path: 'rate/:id',component:RateComponent},
  { path: 'user-list',component:UserListComponent},
  { path: 'update-user/:id', component:UpdateUserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
