import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {MatIconRegistry} from "@angular/material/icon";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'EasyEmploy';

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {

    this.httpClient
      .get<any>('/api/getAuthenticatedUser')
      .subscribe((baseUser) => {
        if (baseUser.role == "" && this.router.url != "/create-account" && this.router.url != "/login" && !this.router.url.includes("/confirm-account"))
          this.router.navigate(['login']);
      });
  }

}

