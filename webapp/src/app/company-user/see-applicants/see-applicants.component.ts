import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../service/post.service";

@Component({
  selector: 'app-see-applicants',
  templateUrl: './see-applicants.component.html',
  styleUrls: ['./see-applicants.component.css']
})
export class SeeApplicantsComponent implements OnInit {
  users: User[] = [];
  postId: number = 0;

  constructor(private route: ActivatedRoute,
              private service: PostService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.postId = +this.route.snapshot.paramMap.get('id');
    this.service.getApplicants(this.postId).subscribe(
      (users) => this.users = users
    );
  }

}
