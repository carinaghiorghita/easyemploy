import { Component, OnInit } from '@angular/core';
import {Post} from "../../model/post.model";
import {DashboardService} from "../../service/dashboard.service";

@Component({
  selector: 'app-jobs-applied',
  templateUrl: './jobs-applied.component.html',
  styleUrls: ['./jobs-applied.component.css']
})
export class JobsAppliedComponent implements OnInit {
  posts: Post[] = [];

  constructor(private service: DashboardService) { }

  ngOnInit(): void {
    this.service.getPostsApplied().subscribe(posts => {
      this.posts = posts;
    });
  }
}
