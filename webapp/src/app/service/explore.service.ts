import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user.model";
import {Post} from "../model/post.model";
import {Company} from "../model/company.model";

@Injectable({
  providedIn: 'root'
})
export class ExploreService {

  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<User[]>{
    return this.httpClient.get<User[]>('/api/getUsers')
  }

  getCompanies(): Observable<Company[]>{
    return this.httpClient.get<Company[]>('/api/getCompanies')
  }

  getPosts(): Observable<Post[]>{
    return this.httpClient.get<Post[]>('/api/getPosts')
  }
}
