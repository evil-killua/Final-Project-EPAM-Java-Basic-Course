import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user.model';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  id!: number;
  user:User = new User();
  error: string = '';

  adminBefore:boolean =false;
  teacherBefore:boolean = false;
  adminAfter:boolean =false;
  teacherAfter:boolean = false;


  constructor(private userService:UserService,private route:ActivatedRoute,
    private router:Router) { }

  ngOnInit(): void {

    this.id = this.route.snapshot.params['id'];

    this.userService.getUserById(this.id).subscribe(data =>{
      console.log(data)
      this.user=data;

      for(let role of this.user.roles!){
        console.log(role);
        
        if(role === 'ROLE_TEACHER'){
          this.teacherBefore = true;
          this.teacherAfter = true;
        }else if(role === 'ROLE_ADMIN'){
          this.adminBefore = true;
          this.adminAfter = true;
        }

      }
    },
    error => console.log(error));
  }

  onSubmit(){
    
    if(this.user.firstName!==null && this.user.firstName!=='' && this.user.lastName!==null && this.user.lastName!==''&&
        this.user.phone!==null && this.user.phone!=='' && this.user.email!==null && this.user.email!==''){
           

          if(this.adminBefore==false && this.adminAfter!=this.adminBefore){
            this.user.roles!.push('ROLE_ADMIN');
          }else if(this.adminBefore==true && this.adminAfter!=this.adminBefore){
            this.user.roles!.forEach((element,index)=>{
              if(element = 'ROLE_ADMIN'){
                this.user.roles!.splice(index,1);
              }
            });
          }
          
          if(this.teacherBefore==false && this.teacherAfter!=this.teacherBefore){
            this.user.roles!.push('ROLE_TEACHER');
          }else if(this.teacherBefore==true && this.teacherAfter!=this.teacherBefore){
            this.user.roles!.forEach((element,index)=>{
              if(element = 'ROLE_TEACHER'){
                this.user.roles!.splice(index,1);
              }
            });
          }

          this.userService.updateUser(this.user).subscribe(data=>{
            console.log(data);
  
            this.goToUserList();
          });

        }else{
          this.error = 'All fields are mandatory';
        }
  }

  goToUserList(){
    this.router.navigateByUrl("user-list");
  }

  
}
