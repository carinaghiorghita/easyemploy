import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatRadioModule } from '@angular/material/radio';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAccountComponent } from './unauthenticated-user/create-account/create-account.component';
import { LoginComponent } from './unauthenticated-user/login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {LoginService} from "./service/login.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConfirmAccountComponent } from './unauthenticated-user/confirm-account/confirm-account.component';
import { ResendConfirmationComponent } from './unauthenticated-user/resend-confirmation/resend-confirmation.component';
import {MatFormField, MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {ConfirmAccountService} from "./service/confirm-account.service";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatCardContent, MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import { HeaderComponent } from './commons/header/header.component';
import { ResetPasswordSendMailComponent } from './unauthenticated-user/reset-password-send-mail/reset-password-send-mail.component';
import { ResetPasswordComponent } from './unauthenticated-user/reset-password/reset-password.component';
import { AccountSuccessfullyCreatedComponent } from './unauthenticated-user/account-successfully-created/account-successfully-created.component';
import { PasswordSuccessfullyResetComponent } from './unauthenticated-user/password-successfully-reset/password-successfully-reset.component';
import {ProfileComponent} from './commons/profile/profile.component';
import {ProfileService} from "./service/profile.service";
import {MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule} from "@angular/material/dialog";
import { DeleteDialogComponent } from './commons/delete-dialog/delete-dialog.component';
import { DashboardUserComponent } from './applicant-user/dashboard-user/dashboard-user.component';
import { DashboardCompanyComponent } from './company-user/dashboard-company/dashboard-company.component';
import { ExplorePeopleComponent } from './commons/explore-people/explore-people.component';
import { ExploreCompaniesComponent } from './commons/explore-companies/explore-companies.component';
import { ExploreJobsComponent } from './commons/explore-jobs/explore-jobs.component';

@NgModule({
  declarations: [
    //unauth
    AppComponent,
    CreateAccountComponent,
    LoginComponent,
    ConfirmAccountComponent,
    ResendConfirmationComponent,
    HeaderComponent,
    ResetPasswordSendMailComponent,
    ResetPasswordComponent,
    AccountSuccessfullyCreatedComponent,
    PasswordSuccessfullyResetComponent,

    //commons
    ProfileComponent,
    DeleteDialogComponent,

    //user
    DashboardUserComponent,

    //company
    DashboardCompanyComponent,
      ExplorePeopleComponent,
      ExploreCompaniesComponent,
      ExploreJobsComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDialogModule
  ],
  providers: [
    LoginService,
    ConfirmAccountService,
    ProfileService,
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}
  ],
  bootstrap: [AppComponent],
  entryComponents: [DeleteDialogComponent]
})
export class AppModule { }
