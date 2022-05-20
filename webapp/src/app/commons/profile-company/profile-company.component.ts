import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {ProfileService} from "../../service/profile.service";
import {Company} from "../../model/company.model";

@Component({
  selector: 'app-profile-company',
  templateUrl: './profile-company.component.html',
  styleUrls: ['./profile-company.component.css']
})
export class ProfileCompanyComponent implements OnInit {
  company: Company = new Company();
  authUser: User = new User();
  follows: boolean = false;

  constructor(private route: ActivatedRoute,
              private service: ProfileService,
              private router: Router) { }

  ngOnInit(): void {
    // @ts-ignore
    const id = +this.route.snapshot.paramMap.get('id');
    this.service.getCompanyById(id)
      .subscribe(company => {
        this.company = company;
        this.service.getUser().subscribe(user => {
          this.authUser=user;
          if(user.id!==0){
            this.service.getFollowedCompanies().subscribe(companies => {
              companies.forEach((company: { id: number; }) => {
                if(company.id===id)
                  this.follows = true;
              })
            });
          }
        });
      });
  }

  onFollow(){
    this.service.follow(this.company).subscribe(() => this.ngOnInit());
  }

  onUnfollow(){
    this.service.unfollow(this.company).subscribe(() =>
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([`/profile-company/${this.company.id}`]);
    }
    ));
  }
}
