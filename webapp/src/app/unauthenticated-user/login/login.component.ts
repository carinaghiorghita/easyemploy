import {Component, OnInit} from '@angular/core';
import {BaseUser} from "../../model/baseuser.model";
import {LoginService} from "../../service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = "";
  password: string = "";
  errorMessage: string = "";

  constructor(private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    let loginUser = <BaseUser>{username: this.username, password: this.password};
    this.service.login(loginUser).subscribe( (user) => {
      this.router.navigateByUrl(`/dashboard-${user.role.toLowerCase()}`).then();
    },
      error => {
      this.errorMessage = error.error.message;
    });
  }

  onResendConfirmation(){
    let loginUser = <BaseUser>{username: this.username, password: this.password};
    this.service.resendConfirmation(loginUser).subscribe();
  }
}
