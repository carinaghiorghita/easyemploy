import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../service/login.service";
import {Router} from "@angular/router";
import {BaseUser} from "../../model/baseuser.model";

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {
  email: string = "";
  password: string = "";
  role: string = "";

  errorMessage: string="";

  constructor(private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.role === "")
      this.errorMessage = "Please choose a role.";
    else {
      let newUser = <BaseUser>{email: this.email, password: this.password, role: this.role};
      this.service.createAccount(newUser)
        .subscribe((user: any) => {
            this.router.navigate(['/resend-confirmation']).then();
          },
          error => this.errorMessage = error.error.message
        );
    }
  }
}
