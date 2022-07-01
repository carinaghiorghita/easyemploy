import { Component, OnInit } from '@angular/core';
import {FileUploadService} from "../../service/file-upload.service";
import {JobApplication} from "../../model/job.application.model";
import {ActivatedRoute} from "@angular/router";
import {FileModel} from "../../model/file.model";
import * as moment from "moment";
// @ts-ignore
import {v4 as uuidv4} from 'uuid';

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.css']
})
export class ApplicationComponent implements OnInit {

  application: JobApplication = new JobApplication();
  CV: FileModel = new FileModel();
  CL: FileModel = new FileModel();
  readonly today = moment();

  feedback: boolean = false;
  interview: boolean = false;

  feedbackText: string = "";
  interviewText: string = "";
  interviewLink: string = "";

  constructor(private service: FileUploadService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    // @ts-ignore
    const postId = +this.route.snapshot.paramMap.get('id1');
    // @ts-ignore
    const userId = +this.route.snapshot.paramMap.get('id2');

    this.service.getApplication(userId,postId).subscribe(
      (app: JobApplication) => {
        this.application = app;
        this.service.getFile(app.cvid).subscribe((CV) => this.CV = CV);
        this.service.getFile(app.clid).subscribe((CL) => this.CL = CL);

        if (this.application.feedback === "" || this.application.feedback === null)
          this.feedbackText = "Send feedback";
        else
          this.feedbackText = "Edit feedback";

        if (this.application.interviewLink === "" || this.application.interviewLink === null)
          this.interviewText = "Call to interview";
        else
          this.interviewText = "Edit interview details";
      });
  }

  toggleFeedback(){
    this.feedback = !this.feedback;
    if(!this.feedback) {
      if (this.application.feedback === "")
        this.feedbackText = "Send feedback";
      else
        this.feedbackText = "Edit feedback";
    }
    else
      this.feedbackText = "Close feedback";
  }

  toggleCall(){
    this.interview = !this.interview;

    if(!this.interview) {
      if (this.application.interviewLink === "")
        this.interviewText = "Call to interview";
      else
        this.interviewText = "Edit interview details";
    }
    else {

      this.interviewLink = "http://localhost:4200/video-session/" + uuidv4();

      this.interviewText = "Close";
    }
  }

  sendFeedback(){
    this.service.sendFeedback(this.application).subscribe(
      () => window.location.reload()
    );
  }

  sendInterview(){
    this.application.interviewLink = this.interviewLink;
    this.service.sendFeedback(this.application).subscribe(
      () => window.location.reload()
    );
  }

}
