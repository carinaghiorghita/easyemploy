import {Component, Input, OnInit} from '@angular/core';
import {MatIconRegistry} from "@angular/material/icon";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  role: string = "";

  @Input() link: string | undefined;

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.httpClient
      .get<any>('/api/getAuthenticatedUser')
      .subscribe( (user) =>
        this.role = user.role
      )
  }

  logout(): void {
    this.httpClient.get<any>('/api/logout').subscribe(() =>
      this.router.navigate(['login'])
    );
  }
}
