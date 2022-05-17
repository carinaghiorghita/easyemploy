import { Component, OnInit } from '@angular/core';
import {Post} from "../../model/post.model";
import {DashboardCompanyService} from "../../service/dashboard-company.service";

@Component({
  selector: 'app-dashboard-company',
  templateUrl: './dashboard-company.component.html',
  styleUrls: ['./dashboard-company.component.css']
})
export class DashboardCompanyComponent implements OnInit {
  posts: Post[] = [];

  constructor(private service: DashboardCompanyService) { }

  ngOnInit(): void {
    this.service.getPostsForCurrentCompany().subscribe(posts => {
      this.posts = posts;
    })
  }

}
