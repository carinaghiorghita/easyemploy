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
              private router: Router) { }

  ngOnInit(): void {

    // this.httpClient
    //   .get<any>('/api/getUser')
    //   .subscribe((user) => {
    //     console.log(user);
    //     if (user.role == "" && this.router.url != "/createAccount" && this.router.url != "/login")
    //       this.router.navigate(['login']);
    //   });
    //this.router.navigate(['login']);
  }

}

