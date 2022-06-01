import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {FileModel} from "../model/file.model";
import {JobApplication} from "../model/job.application.model";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http:HttpClient) { }

  uploadFiles(CV: File, CL: File):Observable<any> {
    let formData: FormData = new FormData();
    formData.append('CV', CV, CV.name);
    if(CL) {
      formData.append('CL', CL, CL.name);
      return this.http.post<any>('/api/uploadFiles', formData);
    }
    return this.http.post<any>('/api/uploadFilesCV', formData);
  }

  saveApplication(jobApplication: JobApplication){
    console.log(jobApplication);
    return this.http.post<any>('/api/saveApplication', jobApplication);
  }

  processCV(CV: File):Observable<HttpEvent<any>> {
    let formData: FormData = new FormData();
    formData.append('CV', CV, CV.name);
    return this.http.post<HttpEvent<any>>('/api/processCV', formData);
  }


  getFile(id: number):Observable<any>{
    const params = new HttpParams().set('id', Number(id))
    return this.http.get('/api/getFile', {params});
  }
}
