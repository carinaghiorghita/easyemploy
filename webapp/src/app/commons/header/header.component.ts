import {Component, NgZone, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  role: string = "";
  link: string = "";

  constructor(private httpClient: HttpClient,
              private router: Router,
              private zone: NgZone) {
    router.events.subscribe((val) => {
      if(val instanceof NavigationEnd){
        if(val.url == '/login' || val.url.includes('/dashboard')){
          this.ngOnInit();
        }
      }
    })
  }

  ngOnInit(): void {
    this.httpClient
      .get<any>('/api/getUserRole')
      .subscribe((user) => {
          this.role = user.role;
          if (this.role == "UNAUTH") {
            this.link = '/login';
          }
          else {
            this.link = `/dashboard-${this.role.toLowerCase()}`;
          }
        }
      );
  }

  logout(): void {
    this.httpClient.get<any>('/api/logout').subscribe(() => {
      this.zone.run(() => {
        this.router.navigateByUrl('/login').then();
      });
      }
    );
  }

}
