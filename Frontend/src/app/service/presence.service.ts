import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Presence } from "../model/presence.model";



@Injectable({
    providedIn:"root"
})
export class PresenceService{

	private baseUrl = 'http://localhost:8080/presence';

    constructor(private httpClient:HttpClient){}

    createPresence(presences:Presence[]):Observable<string>{
        return this.httpClient.post<string>(`${this.baseUrl}`,presences, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    getCoursePresence(courseName:string):Observable<Presence[]>{
        return this.httpClient.get<Presence[]>(`${this.baseUrl}/${courseName}`);
    }

    getPresenceByCourseNameAndTopicName(courseName:string,topicName:string):Observable<Presence[]>{
        return this.httpClient.get<Presence[]>(`${this.baseUrl}/get/${courseName}/${topicName}`);
    }

    getPresenceByCourseNameAndUserName(courseName:string, userName:string):Observable<Presence[]>{
        return this.httpClient.get<Presence[]>(`${this.baseUrl}/${courseName}/${userName}`);
    }

}