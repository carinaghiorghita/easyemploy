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

  excelLink: string = "";

  constructor(private route: ActivatedRoute,
              private service: PostService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.postId = +this.route.snapshot.paramMap.get('id');
    this.excelLink = `http://localhost:4200/api/getExcel/${this.postId}`;
    this.service.getApplicants(this.postId).subscribe(
      (users) => this.users = users
    );
  }

  searchText: string = '';

  onSearchTextEntered(searchValue: string){
    this.searchText = searchValue;
    console.log(this.searchText);
  }
}
