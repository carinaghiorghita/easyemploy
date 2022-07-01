import { Component, OnInit } from '@angular/core';
import {JobApplication} from "../../model/job.application.model";
import {FileUploadService} from "../../service/file-upload.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DeletePostDialogComponent} from "../../commons/delete-post-dialog/delete-post-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {DeleteApplicationDialogComponent} from "../../commons/delete-application-dialog/delete-application-dialog.component";
import * as moment from 'moment';

@Component({
  selector: 'app-edit-application',
  templateUrl: './edit-application.component.html',
  styleUrls: ['./edit-application.component.css']
})
export class EditApplicationComponent implements OnInit {
  userId: number = 0;
  CV: File | undefined;
  CL: File | undefined;
  jobApplication: JobApplication = new JobApplication();

  readonly salutations: string[] = ['Mr.', 'Mrs.', 'Ms.', 'None'];
  readonly minDate = moment().subtract(100,'years');
  readonly maxDate = moment().subtract(16,'years');

  message = '';

  constructor(private service: FileUploadService,
              private route: ActivatedRoute,
              private router: Router,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    // @ts-ignore
    this.jobApplication.postId = +this.route.snapshot.paramMap.get('id1');
    // @ts-ignore
    this.userId = +this.route.snapshot.paramMap.get('id2');

    this.service.getApplication(this.userId, this.jobApplication.postId).subscribe(
      (jobApplication) => {
        this.jobApplication = jobApplication;
      }
    );
  }

  selectCV(event: Event) {
    // @ts-ignore
    this.CV = event.target.files[0];
    this.service.processCV(this.CV as File).subscribe(
      event => {
        // @ts-ignore
        this.message = event.message;

        this.jobApplication.firstName = this.message.split(" ")[0];
        this.jobApplication.lastName = this.message.split(" ")[1];
        this.jobApplication.email = this.message.split(" ")[2];
      },
      err => {
        console.log(err);
        this.message = 'Could not process CV! Please try again.';
      }
    );
  }

  selectCL(event: Event) {
    // @ts-ignore
    this.CL = event.target.files[0];
  }

  onSubmit(): void {
    if(this.jobApplication.salutations==="None")
    this.jobApplication.salutations="";
    this.service.editApplication(this.CV as File, this.CL as File, this.jobApplication.postId).subscribe(
      () => this.service.updateApplication(this.jobApplication).subscribe(
        () => this.router.navigateByUrl(`/view-post/${this.jobApplication.postId}`)
      )
    );
  }

  removeApplication(): void {
    const dialogRef = this.dialog.open(DeleteApplicationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.service.removeApplication(this.jobApplication).subscribe(() =>
          this.router.navigateByUrl(`/view-post/${this.jobApplication.postId}`)
        );
      }
    });
  }

}
