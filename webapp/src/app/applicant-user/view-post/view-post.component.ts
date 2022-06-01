import { Component, OnInit } from '@angular/core';
import {PostService} from "../../service/post.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../../model/post.model";
import {HttpClient} from "@angular/common/http";
import {DeletePostDialogComponent} from "../../commons/delete-post-dialog/delete-post-dialog.component";
import {UserLoginDialogComponent} from "../../commons/user-login-dialog/user-login-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {
  post: Post = new Post();
  buttonText: string = "";

  constructor(private service: PostService,
              private route: ActivatedRoute,
              private httpClient: HttpClient,
              private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    // @ts-ignore
    const id = +this.route.snapshot.paramMap.get('id');

    this.service.getPost(id)
      .subscribe(post => {
        this.post = post;
      });

    this.service.hasApplied(id).subscribe(
      (result) => this.buttonText = result ? "Edit application" : "Apply"
    )
  }

  onApply(): void {
    this.httpClient
      .get<any>('/api/getAuthenticatedUser')
      .subscribe((user) => {
        if(user.role==='USER'){
          if(this.buttonText==="Apply")
            this.router.navigate(["/apply",this.post.id]);
          else
            this.router.navigate(["/edit-application",this.post.id, user.id]);
        }
        else {
          const dialogRef = this.dialog.open(UserLoginDialogComponent);

          dialogRef.afterClosed().subscribe(result => {
            if (result === true) {
              this.router.navigateByUrl('/login');
            }
          });

        }
      })
  }
}
