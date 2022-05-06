import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {ConfirmAccountService} from "../service/confirm-account.service";

@Component({
  selector: 'app-confirm-account',
  templateUrl: './confirm-account.component.html',
  styleUrls: ['./confirm-account.component.css']
})
export class ConfirmAccountComponent implements OnInit {

  token: string="";

  constructor(private route: ActivatedRoute,
              private confirmAccountService: ConfirmAccountService) { }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {
        this.token = params['token'];
        this.confirmAccountService.confirm(this.token).subscribe(() => {});
      });
  }

}
