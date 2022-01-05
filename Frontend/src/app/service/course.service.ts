import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Course } from "../model/course.model";
import { map } from 'rxjs/operators';

@Injectable({
    providedIn:"root"
})
export class CourseService{

	private baseUrl = 'http://localhost:8080/course';

    constructor(private httpClient:HttpClient){}

    getCourses(userName:string):Observable<Course[]>{
        return this.httpClient.get<Course[]>(`${this.baseUrl}/all/${userName}`);
    }

    getCourseById(id:number):Observable<Course>{
        return this.httpClient.get<Course>(`${this.baseUrl}/${id}`);
    }

    getCourseByUserName(username:string):Observable<Course[]>{
        return this.httpClient.get<Course[]>(`${this.baseUrl}/view/${username}`)
    }

    getTeacherCourseByUserName(username:string):Observable<Course[]>{
        return this.httpClient.get<Course[]>(`${this.baseUrl}/teacher/${username}`)
    }

    addCourse(course:Course):Observable<any>{
        return this.httpClient.post<any>(`${this.baseUrl}`,course, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    deleteCourse(id:number):Observable<any>{
        return this.httpClient.delete<any>(`${this.baseUrl}/${id}`,{responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    updateCourse(course:Course):Observable<any>{
        return this.httpClient.put<any>(`${this.baseUrl}`,course,{responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }


}