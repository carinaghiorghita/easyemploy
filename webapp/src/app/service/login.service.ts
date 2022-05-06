import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BaseUser} from "../model/baseuser.model";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) {
  }

  login(user: BaseUser): Observable<any> {
    return this.httpClient
      .post<any>('/api/login', user);
  }

  createAccount(user: BaseUser): Observable<any>{
    return this.httpClient
      .post<any>('/api/create-account', user);
  }

  logout(): Observable<any> {
    return this.httpClient
      .get<any>('/api/logout');
  }

}
