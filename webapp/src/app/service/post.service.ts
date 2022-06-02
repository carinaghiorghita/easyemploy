import { Injectable } from '@angular/core';
import {Post} from "../model/post.model";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "../model/user.model";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  savePost(post: Post): Observable<any>{
    return this.httpClient.post<any>('/api/savePost',post);
  }

  updatePost(post: Post): Observable<any>{
    return this.httpClient.post<any>('/api/updatePost',post);
  }

  deletePost(id: number): Observable<any>{
    const params = new HttpParams().set('id', Number(id));
    return this.httpClient.delete<any>('/api/deletePost',{params});
  }

  getPost(id: number): Observable<any>{
    const params = new HttpParams().set('id', Number(id));
    return this.httpClient.get<any>('/api/getPost', {params});
  }

  hasApplied(id: number): Observable<boolean>{
    const params = new HttpParams().set('id', Number(id));
    return this.httpClient.get<any>('/api/hasApplied', {params});
  }

  getApplicants(postId: number): Observable<User[]>{
    const params = new HttpParams().set('postId', Number(postId));
    return this.httpClient.get<any>('/api/getApplicants', {params});
  }

  getApplication(postId: number): Observable<any>{
    const params = new HttpParams().set('postId', Number(postId));
    return this.httpClient.get<any>('/api/getApplicationPost', {params});
  }
}
