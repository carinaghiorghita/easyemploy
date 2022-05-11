import { Component, OnInit } from '@angular/core';
import {BaseUser} from "../../model/baseuser.model";
import {User} from "../../model/user.model";
import {Company} from "../../model/company.model";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmAccountService} from "../../service/confirm-account.service";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  token: string="";
  errorMessage: string = "";
  creationError: string = "";

  email: string="";
  baseUser: BaseUser=new BaseUser();

  constructor(private route: ActivatedRoute,
              private confirmAccountService: ConfirmAccountService,
              private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {
        this.token = params['token'];
      });

    this.confirmAccountService.confirm(this.token).subscribe((tokenObj) => {
        this.email = tokenObj.email;
        this.confirmAccountService.getUser(this.email).subscribe((user: BaseUser) => {
          this.baseUser=user;
          this.baseUser.password="";
        });
      },
      error => {
        this.errorMessage = error.error.message;
      }
    );
  }

  onSubmit(): void {
      this.confirmAccountService.resetPassword(this.baseUser).subscribe(
        () => {
          this.router.navigate(['/password-successfully-reset']);
        },
        error => this.creationError = error.error.message
      );
  }

  onResendConfirmation(){
    this.confirmAccountService.resendConfirmation(this.token).subscribe();
  }
}
