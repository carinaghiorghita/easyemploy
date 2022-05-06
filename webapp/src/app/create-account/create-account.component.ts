import { Component, OnInit } from '@angular/core';
import {LoginService} from "../service/login.service";
import {Router} from "@angular/router";
import {BaseUser} from "../model/baseuser.model";

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {
  email: string = "";
  password: string = "";
  role: string = "";

  constructor(private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    let newUser = <BaseUser>{email: this.email, password: this.password, role: this.role};
    this.service.createAccount(newUser)
      .subscribe( (user: any) => {console.log(user);
        //this.router.navigate(['login'])
        });
  }

}
