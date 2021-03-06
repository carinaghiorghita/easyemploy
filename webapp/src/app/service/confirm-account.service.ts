import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {BaseUser} from "../model/baseuser.model";
import {User} from "../model/user.model";
import {Company} from "../model/company.model";
import {Token} from "../model/token.model";

@Injectable({
  providedIn: 'root'
})
export class ConfirmAccountService {

  constructor(private httpClient: HttpClient) { }

  confirm(token: String): Observable<Token>{
    const params = new HttpParams().set('token', String(token))

    return this.httpClient
      .get<any>('/api/confirm-account', {params});
  }

  resendConfirmation(expiredToken: String): Observable<Token>{
    const params = new HttpParams().set('expiredToken', String(expiredToken))

    return this.httpClient
      .get<any>('/api/resend-confirmation-expired', {params});
  }

  resetPassword(baseUser: BaseUser): Observable<any>{
    return this.httpClient.post<any>('/api/setNewPassword', baseUser);
  }


  getUser(email: string): Observable<any>{
    email=email.replace("+","%2B");
    const params = new HttpParams().set('email', String(email))

    return this.httpClient
      .get<any>('/api/getUserByEmail', {params});
  }

  updateUser(user: User): Observable<any>{
    return this.httpClient.post<any>('/api/updateUser', user);
  }

  updateCompany(company: Company): Observable<any>{
    return this.httpClient.post<any>('/api/updateCompany', company);
  }
}
