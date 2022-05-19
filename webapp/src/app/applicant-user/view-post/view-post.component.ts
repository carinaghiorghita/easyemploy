import { Component, OnInit } from '@angular/core';
import {PostService} from "../../service/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../model/post.model";

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  post: Post = new Post();

  constructor(private service: PostService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    // @ts-ignore
    const id = +this.route.snapshot.paramMap.get('id');
    this.service.getPost(id)
      .subscribe(post => {
        this.post = post;
      });
  }


}
