import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';

import { NgForm } from '@angular/forms';

export class IUser {
  id!:number;
	userName!: string;
	userPwd!: string;
  firstName!:string;
  lastName!:string;
  email!:string;
  phone!:string;
	roles: string[] = [];
	showPassword!: boolean
  }

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {


  user!:IUser;

  error: string = '';
  success: string = '';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
	this.user = new IUser();
    this.user.roles!.push('ROLE_USER');
  }

  public doSignup(form: NgForm): void {
    if (form.invalid) {
      for (const control of Object.keys(form.controls)) {
        form.controls[control].markAsTouched();
      }
	    console.log("create1 " + this.user.roles);
	  
      return;
    }else{

		this.authService.signup(this.user).subscribe(
			(result: string) => {

			  this.success = result;
			},
			(err: any) => {
			  console.log(err);

	          this.error = 'User with this username "' + this.user.userName + '" already exists';
			}
		  );

		console.log("create2 " + this.user.roles);
	}

  }
}
