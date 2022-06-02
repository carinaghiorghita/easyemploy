import { Component, OnInit } from '@angular/core';
import {FileUploadService} from "../../service/file-upload.service";
import {JobApplication} from "../../model/job.application.model";
import {ActivatedRoute} from "@angular/router";
import {FileModel} from "../../model/file.model";

@Component({
  selector: 'app-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.css']
})
export class ApplicationComponent implements OnInit {

  application: JobApplication = new JobApplication();
  CV: FileModel = new FileModel();
  CL: FileModel = new FileModel();

  feedback: boolean = false;
  interview: boolean = false;

  feedbackText: string = "Send feedback";
  interviewText: string = "Call to interview";

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

        console.log(app);
      });
  }

  toggleFeedback(){
    this.feedback = !this.feedback;
    this.feedbackText = this.feedback ? "Close feedback" : "Send feedback";
  }

  toggleCall(){
    this.interview = !this.interview;
    this.interviewText = this.interview ? "Close" : "Call to interview";
  }

}
