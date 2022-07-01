import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpParams} from "@angular/common/http";
import {empty, Observable, of} from "rxjs";
import {FileModel} from "../model/file.model";
import {JobApplication} from "../model/job.application.model";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http:HttpClient) { }

  uploadFiles(CV: File, CL: File, postId: number):Observable<any> {
    let formData: FormData = new FormData();
    formData.append('post',String(postId));
    formData.append('CV', CV, CV.name);
    if(CL) {
      formData.append('CL', CL, CL.name);
      return this.http.post<any>('/api/uploadFiles', formData);
    }
    return this.http.post<any>('/api/uploadFilesCV', formData);
  }

  editApplication(CV: File, CL: File, postId: number):Observable<any> {
    let formData: FormData = new FormData();
    formData.append('post',String(postId));
    if(CL) {
      formData.append('CL', CL, CL.name);
      if(CV){
        formData.append('CV', CV, CV.name);
        return this.http.post<any>('/api/uploadFiles', formData);
      }
      return this.http.post<any>('/api/uploadFilesCL', formData);
    }
    else if (CV)
      return this.http.post<any>('/api/uploadFilesCV', formData);
    return of(new FileModel());
  }

  saveApplication(jobApplication: JobApplication){
    return this.http.post<any>('/api/saveApplication', jobApplication);
  }

  updateApplication(jobApplication: JobApplication){
    return this.http.post<any>('/api/updateApplication', jobApplication);
  }

  sendFeedback(jobApplication: JobApplication){
    return this.http.post<any>('/api/sendFeedback', jobApplication);
  }

  // sendInterview(jobApplication: JobApplication){
  //   return this.http.post<any>('/api/sendInterview', jobApplication);
  // }

  removeApplication(jobApplication: JobApplication): Observable<any>{
    const params = new HttpParams().set('userId', jobApplication.userId).set('postId',jobApplication.postId);
    return this.http.delete('/api/removeApplication', {params});
  }

  getApplication(userId: number, postId: number):Observable<any>{
    const params = new HttpParams().set('userId', userId).set('postId',postId);
    return this.http.get('/api/getApplication', {params});
  }

  processCV(CV: File):Observable<HttpEvent<any>> {
    let formData: FormData = new FormData();
    formData.append('CV', CV, CV.name);
    return this.http.post<HttpEvent<any>>('/api/processCV', formData);
  }

  getFile(id: number):Observable<any>{
    const params = new HttpParams().set('id', id);
    return this.http.get('/api/getFile', {params});
  }
}
