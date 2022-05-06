import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ConfirmAccountService {

  constructor(private httpClient: HttpClient) { }

  confirm(token: String): Observable<any>{
    const params = new HttpParams().set('token', String(token))

    return this.httpClient
      .get<any>('/api/confirm-account', {params});
  }
}
