import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../service/auth.service';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

	isSignedin = false;
	isTeacherVisible = false;
	isAdminVisible = false;
	signedinUser: string = '';

	role!:any[];

  constructor(private route: ActivatedRoute, private router: Router, 
		private http: HttpClient, private authService: AuthService) { }

  ngOnInit(): void {
    this.isSignedin = this.authService.isUserSignedin();
		this.signedinUser = this.authService.getSignedinUser();

		if(!this.authService.isUserSignedin()) {
			this.router.navigateByUrl('signin');
		}


		this.getRole();
		console.log("type")
		console.log(typeof this.role);
		for(let r of this.role){
			console.log(r);
			if(r === 'ROLE_TEACHER'){
				this.isTeacherVisible = true;
				break;
			}else if(r === 'ROLE_ADMIN'){
				this.isAdminVisible = true;
			}
		}
  }

  getRole(){
		this.role = this.authService.getRole();
	}

  doSignout() {
		this.authService.signout();
	}

	getCourse(){
		this.router.navigateByUrl("course-view");
	}

	getUsers(){
		this.router.navigateByUrl("user-list");
	}

	getUserCourse(){
		this.router.navigateByUrl("user-course");
	}

	getTeacherCourse(){
		this.router.navigateByUrl("teacher-course");
	}

	createCourse(){
		this.router.navigateByUrl("create-course");
	}
}
