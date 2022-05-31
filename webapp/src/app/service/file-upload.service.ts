import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor(private http:HttpClient) { }

  upload(file: File):Observable<HttpEvent<any>> {
    let formData: FormData = new FormData();
    formData.append('file', file, file.name);
    console.log(formData);
    return this.http.post<HttpEvent<any>>('/api/upload', formData);
  }
}
