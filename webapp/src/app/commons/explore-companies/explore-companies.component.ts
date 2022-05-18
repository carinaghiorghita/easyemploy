import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {ExploreService} from "../../service/explore.service";
import {Company} from "../../model/company.model";

@Component({
  selector: 'app-explore-companies',
  templateUrl: './explore-companies.component.html',
  styleUrls: ['./explore-companies.component.css']
})
export class ExploreCompaniesComponent implements OnInit {
  companies: Company[]=[];

  constructor(private service: ExploreService) { }

  ngOnInit(): void {
    this.service.getCompanies().subscribe(companies => {
      this.companies = companies;
    });

  }
}
