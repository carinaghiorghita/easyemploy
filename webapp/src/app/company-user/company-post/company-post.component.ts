import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {PostService} from "../../service/post.service";
import {Post} from "../../model/post.model";
import {DeletePostDialogComponent} from "../../commons/delete-post-dialog/delete-post-dialog.component";

@Component({
  selector: 'app-company-post',
  templateUrl: './company-post.component.html',
  styleUrls: ['./company-post.component.css']
})
export class CompanyPostComponent implements OnInit {
  errorMessage: string = ""
  buttonText: string = "Edit";
  disabled: boolean = true;

  post: Post = new Post();

  readonly levels: string[] = ['Internship', 'Junior', 'Mid-Level', 'Senior','Any'];

  constructor(private service: PostService,
              private router: Router,
              public dialog: MatDialog,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // @ts-ignore
    const id = +this.route.snapshot.paramMap.get('id');
    this.service.getPost(id)
      .subscribe(post => {
        this.post = post;
      });
  }

  onSubmit(): void {
    if (this.disabled) {
      this.disabled = false;
      this.buttonText = "Save";
    } else {
      this.service.updatePost(this.post).subscribe(
        () => {
          this.onExitEdit()
        },
        error => this.errorMessage = error.error.message
      );
    }
  }

  onExitEdit(): void {
    this.disabled = true;
    this.buttonText = "Edit";
    this.errorMessage = "";
  }

  deletePost(): void {
    const dialogRef = this.dialog.open(DeletePostDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.service.deletePost(this.post.id).subscribe(() =>
          this.router.navigateByUrl('/dashboard-company')
        );
      }
    });
  }
}
