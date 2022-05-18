import { Injectable } from '@angular/core';
import {Post} from "../model/post.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  savePost(post: Post): Observable<any>{
    return this.httpClient.post<any>('/api/savePost',post);
  }
}
