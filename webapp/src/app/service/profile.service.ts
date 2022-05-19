import { Injectable } from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Company} from "../model/company.model";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private httpClient: HttpClient) { }

  getUser(): Observable<User> {
    return this.httpClient.get<User>('/api/getUser');
  }

  getUserById(id: number): Observable<User> {
    const params = new HttpParams().set('id', Number(id))
    return this.httpClient.get<User>('/api/getUserById', {params});
  }

  getCompany(): Observable<Company> {
    return this.httpClient.get<Company>('/api/getCompany');
  }

  getCompanyById(id: number): Observable<Company> {
    const params = new HttpParams().set('id', Number(id))
    return this.httpClient.get<Company>('/api/getCompanyById', {params});
  }

  updateUser(user: User): Observable<any>{
    return this.httpClient.post<any>('/api/updateUser', user);
  }

  updateCompany(company: Company): Observable<any>{
    return this.httpClient.post<any>('/api/updateCompany', company);
  }

  deleteAccount(username: string): Observable<any>{
    const params = new HttpParams().set('username', String(username))

    return this.httpClient.delete<any>('/api/deleteAccount',{params});
  }
}
