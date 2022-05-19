import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../../service/profile.service";
import {User} from "../../model/user.model";

@Component({
  selector: 'app-profile-user',
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {
  user: User = new User();

  constructor(private route: ActivatedRoute,
              private service: ProfileService) { }

  ngOnInit(): void {
    // @ts-ignore
    const id = +this.route.snapshot.paramMap.get('id');
    this.service.getUserById(id)
      .subscribe(user => {
        this.user = user;
      });
  }

}
