import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Course } from "../model/course.model";
import { map } from 'rxjs/operators';
import { Archive } from "../model/archive.model";
import { User } from "../model/user.model";

@Injectable({
    providedIn:"root"
})
export class UserService{

	private baseUrl = 'http://localhost:8080/user';

    constructor(private httpClient:HttpClient){}

    getUserByCourseName(courseName:string):Observable<User[]>{
        return this.httpClient.get<User[]>(`${this.baseUrl}/student/${courseName}`);
    }

    /*getUserByCourseId(id:number):Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseUrl}/course-user/${id}`);
    }*/

    getTeacher():Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseUrl}/teacher`)
    }

    getUsers():Observable<User[]>{
        return this.httpClient.get<User[]>(`${this.baseUrl}`);
    }

    deleteUser(id:number):Observable<string>{
        return this.httpClient.delete<string>(`${this.baseUrl}/${id}`);
    }

    getUserById(id:number):Observable<User>{
        return this.httpClient.get<User>(`${this.baseUrl}/${id}`);
    }

    updateUser(user:User):Observable<string>{
        return this.httpClient.put<string>(`${this.baseUrl}`,user,{responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

}