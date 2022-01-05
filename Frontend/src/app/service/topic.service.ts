import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';
import { Course } from "../model/course.model";


@Injectable({
    providedIn:"root"
})
export class TopicService{

	private baseUrl = 'http://localhost:8080/topic';

    constructor(private httpClient:HttpClient){}

    getTopicByPattern(pattern:string):Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseUrl}/${pattern}`);
    }

    createTopic(topicName:string):Observable<any>{
        return this.httpClient.post<any>(`${this.baseUrl}`,topicName, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    getTopicByCourseName(courseName:string):Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseUrl}/find/${courseName}`);
    }

    getNotPassedCourseTopicByCourseId(id:number):Observable<string[]>{
        return this.httpClient.get<string[]>(`${this.baseUrl}/passed/${id}`);
    }

}