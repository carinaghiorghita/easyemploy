import { Component, OnInit } from '@angular/core';
import {User} from "../../model/user.model";
import {ExploreService} from "../../service/explore.service";

@Component({
  selector: 'app-explore-people',
  templateUrl: './explore-people.component.html',
  styleUrls: ['./explore-people.component.css']
})
export class ExplorePeopleComponent implements OnInit {
  users: User[]=[];

  constructor(private service: ExploreService) { }

  ngOnInit(): void {
    this.service.getUsersExceptCurrent().subscribe(users => {
      this.users = users;
    });

  }

}
