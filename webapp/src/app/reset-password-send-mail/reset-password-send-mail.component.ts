import { Component, OnInit } from '@angular/core';
import {LoginService} from "../service/login.service";
import {Router} from "@angular/router";
import {BaseUser} from "../model/baseuser.model";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password-send-mail.component.html',
  styleUrls: ['./reset-password-send-mail.component.css']
})
export class ResetPasswordSendMailComponent implements OnInit {
  email: string = "";

  errorMessage: string="";

  constructor(private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
      this.service.resetPassword(this.email)
        .subscribe(() => {
            this.router.navigate(['/resend-confirmation']);
          },
          error => this.errorMessage = error.error.message
        );
    }
}
