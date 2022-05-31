import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpEventType, HttpResponse} from "@angular/common/http";
import {FileUploadService} from "../../service/file-upload.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.css']
})
export class ApplyComponent implements OnInit {
  file: File | undefined;
  message = '';
  constructor(private service: FileUploadService) { }

  ngOnInit(): void {
  }

  selectFile(event: Event) {
    // @ts-ignore
    this.file = event.target.files[0];
  }

  upload() {
    this.service.upload(this.file as File).subscribe(
      event => {
        console.log(event);
        // @ts-ignore
        this.message = event.message;
        console.log(this.message);

      },
      err => {
        console.log(err);
        this.message = 'Could not upload the file!';
      }
    );
  }
}
