import { Component, OnInit } from '@angular/core';
import {BaseUser} from "../model/baseuser.model";
import {LoginService} from "../service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = "";
  password: string = "";

  constructor(private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    let loginUser = <BaseUser>{username: this.username, password: this.password};
    this.service.login(loginUser).subscribe( (user) => {
      //console.log(user);
        //this.router.navigate([user2.role]);
    });
  }

}
