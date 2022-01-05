import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Course } from "../model/course.model";
import { map } from 'rxjs/operators';
import { Archive } from "../model/archive.model";

@Injectable({
    providedIn:"root"
})
export class ArchiveService{

	private baseUrl = 'http://localhost:8080/archive';

    constructor(private httpClient:HttpClient){}

    getArchives():Observable<Archive[]>{
        return this.httpClient.get<Archive[]>(`${this.baseUrl}`);
    }

    getArchiveById(id:number):Observable<Archive>{
        return this.httpClient.get<Archive>(`${this.baseUrl}/${id}`);
    }

    addArchive(archive:Archive):Observable<any>{
        return this.httpClient.post<any>(`${this.baseUrl}`,archive, {responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    deleteArchive(id:number):Observable<any>{
        return this.httpClient.delete<any>(`${this.baseUrl}/${id}`,{responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    updateArchive(archive:Archive[]):Observable<any>{
        return this.httpClient.put<any>(`${this.baseUrl}`,archive,{responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    deduct(courseName:string, username:string):Observable<any>{
        return this.httpClient.delete<any>(`${this.baseUrl}/delete/${username}/${courseName}`,{responseType:'text' as 'json'}).pipe(map((resp)=>{
            return resp;
        }));
    }

    getArchiveByCourseName(courseName:string):Observable<Archive[]>{
        return this.httpClient.get<Archive[]>(`${this.baseUrl}/all/${courseName}`);
    }

    getArchiveByCourseNameAndUserName(courseName:string,username:string):Observable<Archive>{
        return this.httpClient.get<Archive>(`${this.baseUrl}/${courseName}/${username}`);
    }

}