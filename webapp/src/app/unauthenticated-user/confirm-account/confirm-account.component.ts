import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ConfirmAccountService} from "../../service/confirm-account.service";
import {BaseUser} from "../../model/baseuser.model";
import {User} from "../../model/user.model";
import {Company} from "../../model/company.model";

@Component({
  selector: 'app-confirm-account',
  templateUrl: './confirm-account.component.html',
  styleUrls: ['./confirm-account.component.css']
})
export class ConfirmAccountComponent implements OnInit {

  token: string="";
  errorMessage: string = "";
  creationError: string = "";

  email: string="";
  baseUser: BaseUser=new BaseUser();
  user: User=new User();
  company: Company=new Company();

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
      });
    },
      error => {
        this.errorMessage = error.error.message;
      }
    );
  }

  onSubmit(): void {
    if(this.baseUser.role==='USER'){
      this.user.username = this.baseUser.username;
      this.user.phoneNumber = this.baseUser.phoneNumber;
      this.user.email = this.baseUser.email;
      this.user.id = this.baseUser.id;
      this.user.activated = this.baseUser.activated;
      this.user.password = this.baseUser.password;

      this.confirmAccountService.updateUser(this.user).subscribe(() => {
        this.router.navigate(['/account-successfully-created']).then();
        },
        error => this.creationError = error.error.message
      );
    }
    else {
      this.company.username = this.baseUser.username;
      this.company.phoneNumber = this.baseUser.phoneNumber;
      this.company.email = this.baseUser.email;
      this.company.id = this.baseUser.id;
      this.company.activated = this.baseUser.activated;
      this.company.password = this.baseUser.password;


      this.confirmAccountService.updateCompany(this.company).subscribe((
        ) => {
          this.router.navigate(['/account-successfully-created']).then();
        },
        error => this.creationError = error.error.message
      );
    }
  }

  onResendConfirmation(){
    this.confirmAccountService.resendConfirmation(this.token).subscribe();
  }

}
