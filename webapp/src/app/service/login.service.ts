import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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

  resetPassword(email: string): Observable<any>{
    const params = new HttpParams().set('email', String(email))

    return this.httpClient
      .get<any>('/api/reset-password', {params});
  }


  resendConfirmation(user: BaseUser): Observable<any>{
    return this.httpClient
      .post<any>('/api/resend-confirmation', user);
  }

}
