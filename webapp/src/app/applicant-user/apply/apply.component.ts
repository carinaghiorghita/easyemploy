import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpEventType, HttpResponse} from "@angular/common/http";
import {FileUploadService} from "../../service/file-upload.service";
import {JobApplication} from "../../model/job.application.model";
import {ActivatedRoute, Router} from "@angular/router";
import * as moment from 'moment';

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.css']
})
export class ApplyComponent implements OnInit {
  CV: File | undefined;
  CL: File | undefined;
  jobApplication: JobApplication = new JobApplication();

  readonly sals: string[] = ['Mr.', 'Mrs.', 'Ms.', 'None'];
  readonly minDate = moment().subtract(100,'years');
  readonly maxDate = moment().subtract(16,'years');

  message = '';

  constructor(private service: FileUploadService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    // @ts-ignore
    this.jobApplication.postId = +this.route.snapshot.paramMap.get('id');
    this.jobApplication.dob = this.maxDate.toDate();
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
    this.service.uploadFiles(this.CV as File, this.CL as File, this.jobApplication.postId).subscribe(
      () => this.service.saveApplication(this.jobApplication).subscribe(
        () => this.router.navigateByUrl(`/view-post/${this.jobApplication.postId}`)      )
    );
  }
}
