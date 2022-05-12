import { Injectable } from '@angular/core';
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Company} from "../model/company.model";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private httpClient: HttpClient) { }

  getUser(): Observable<User> {
    return this.httpClient.get<User>('/api/getUser');
  }

  getCompany(): Observable<Company> {
    return this.httpClient.get<Company>('/api/getCompany');
  }

  updateUser(user: User): Observable<any>{
    return this.httpClient.post<any>('/api/updateUser', user);
  }

  updateCompany(company: Company): Observable<any>{
    return this.httpClient.post<any>('/api/updateCompany', company);
  }

}
