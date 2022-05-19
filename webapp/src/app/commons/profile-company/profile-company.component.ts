import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../../service/profile.service";
import {Company} from "../../model/company.model";

@Component({
  selector: 'app-profile-company',
  templateUrl: './profile-company.component.html',
  styleUrls: ['./profile-company.component.css']
})
export class ProfileCompanyComponent implements OnInit {
  company: Company = new Company();

  constructor(private route: ActivatedRoute,
              private service: ProfileService) { }

  ngOnInit(): void {
    // @ts-ignore
    const id = +this.route.snapshot.paramMap.get('id');
    this.service.getCompanyById(id)
      .subscribe(company => {
        this.company = company;
      });
  }
}
