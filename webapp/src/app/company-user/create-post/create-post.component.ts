import { Component, OnInit } from '@angular/core';
import {Post} from "../../model/post.model";
import {PostService} from "../../service/post.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  post: Post = new Post;
  error: string="";

  readonly levels: string[] = ['Internship', 'Junior', 'Mid-Level', 'Senior','Any'];

  constructor(private service: PostService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.service.savePost(this.post).subscribe(
      () => {this.router.navigate(['/dashboard-company'])},
      error => {this.error=error.error.message;console.log(error);}
    );
  }

}
