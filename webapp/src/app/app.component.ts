import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

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
        if (baseUser.role == "UNAUTH"
          && this.router.url != "/create-account"
          && this.router.url != "/login"
          && this.router.url != "/account-successfully-created"
          && this.router.url != "/reset-password-send-mail"
          && this.router.url != "/resend-confirmation"
          && !this.router.url.includes("/confirm-account")
          && !this.router.url.includes("/reset-password")
          && !this.router.url.includes("/explore"))
            this.router.navigate(['login']);
      });
  }

}

