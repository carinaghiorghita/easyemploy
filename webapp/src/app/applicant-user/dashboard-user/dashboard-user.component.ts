import { Component, OnInit } from '@angular/core';
import {Post} from "../../model/post.model";
import {DashboardService} from "../../service/dashboard.service";

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrls: ['./dashboard-user.component.css']
})
export class DashboardUserComponent implements OnInit {
  posts: Post[] = [];

  constructor(private service: DashboardService) { }

  ngOnInit(): void {
    this.service.getPostsFromFollowedCompanies().subscribe(posts => {
      this.posts = posts;
    });
  }

}
