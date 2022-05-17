import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../model/post.model";

@Injectable({
  providedIn: 'root'
})
export class DashboardCompanyService {

  constructor(private httpClient: HttpClient) { }

  getPostsForCurrentCompany(): Observable<Post[]>{
    return this.httpClient.get<Post[]>('/api/getPostsForCurrentCompany')
  }
}
