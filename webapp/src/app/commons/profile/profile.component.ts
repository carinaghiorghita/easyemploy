import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ProfileService} from "../../service/profile.service";
import {User} from "../../model/user.model";
import {Company} from "../../model/company.model";
import {Router} from "@angular/router";
import {BaseUser} from "../../model/baseuser.model";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DeleteDialogComponent} from "../delete-dialog/delete-dialog.component";
import {LoginService} from "../../service/login.service";
import {DeletePostDialogComponent} from "../delete-post-dialog/delete-post-dialog.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  role: string = "";
  errorMessage: string = ""
  buttonText: string = "Edit";
  disabled: boolean = true;

  user: User = new User();
  company: Company = new Company();

  constructor(private httpClient: HttpClient,
              private service: ProfileService,
              private router: Router,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.httpClient
      .get<any>('/api/getUserRole')
      .subscribe((user) => {
        this.role = user.role;
        if (this.role == "USER") {
          this.service.getUser().subscribe((user) => this.user = user)
        } else if (this.role == "COMPANY") {
          this.service.getCompany().subscribe((company) => this.company = company)
        } else {
          this.router.navigate(['login']);
        }
      });
  }

  onSubmit(): void {
    if (this.disabled) {
      this.disabled = false;
      this.buttonText = "Save";
    } else {
      if (this.role === 'USER') {
        this.service.updateUser(this.user).subscribe(() => {
            this.onExitEdit()
          },
          error => this.errorMessage = error.error.message
        );
      } else {
        this.service.updateCompany(this.company).subscribe(() => {
            this.onExitEdit()
          },
          error => this.errorMessage = error.error.message
        );
      }
    }
  }

  onExitEdit(): void {
    this.disabled = true;
    this.buttonText = "Edit";
    this.errorMessage = "";
  }

  deleteAccount(): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent);

    var username = this.user.username!=="" ? this.user.username : this.company.username;

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.httpClient.get<any>('/api/logout').subscribe(() => {
            this.service.deleteAccount(username).subscribe(() =>
              this.router.navigateByUrl('/login')
            );
          }
        );
      }
    });
  }
}
