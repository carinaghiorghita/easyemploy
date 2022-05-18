import { Component, OnInit } from '@angular/core';
import {Company} from "../../model/company.model";
import {ExploreService} from "../../service/explore.service";
import {Post} from "../../model/post.model";

@Component({
  selector: 'app-explore-jobs',
  templateUrl: './explore-jobs.component.html',
  styleUrls: ['./explore-jobs.component.css']
})
export class ExploreJobsComponent implements OnInit {
  posts: Post[]=[];

  constructor(private service: ExploreService) { }

  ngOnInit(): void {
    this.service.getPosts().subscribe(posts => {
      this.posts = posts;
    });

  }
}
