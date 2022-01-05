import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { CourseTopic } from "../model/courseTopic.model";



@Injectable({
    providedIn:"root"
})
export class CourseTopicService{

	private baseUrl = 'http://localhost:8080/courseTopic';

    constructor(private httpClient:HttpClient){}

    addCourseTopics(courseTopic:CourseTopic[]):Observable<any>{
        return this.httpClient.post<any>(`${this.baseUrl}`,courseTopic, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    updateCourseTopic(courseTopic:CourseTopic):Observable<string>{
        return this.httpClient.put<any>(`${this.baseUrl}`,courseTopic, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    getDateOfStudyByCourseNameAndTopicName(courseName:string,topicName:string):Observable<string>{
        return this.httpClient.get<any>(`${this.baseUrl}/date/${courseName}/${topicName}`, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    getCourseTopicByCourseName(courseName:string):Observable<CourseTopic[]>{
        return this.httpClient.get<CourseTopic[]>(`${this.baseUrl}/${courseName}`);
    }


}