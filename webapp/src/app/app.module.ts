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
import { MatFormFieldModule} from "@angular/material/form-field";
import {ConfirmAccountService} from "./service/confirm-account.service";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import { MatCardModule} from "@angular/material/card";
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
import { CreatePostComponent } from './company-user/create-post/create-post.component';
import {MatSelectModule} from "@angular/material/select";
import {MAT_DATE_LOCALE, MatNativeDateModule, MatOptionModule} from "@angular/material/core";
import { CompanyPostComponent } from './company-user/company-post/company-post.component';
import { DeletePostDialogComponent } from './commons/delete-post-dialog/delete-post-dialog.component';
import { ViewPostComponent } from './applicant-user/view-post/view-post.component';
import { ProfileCompanyComponent } from './commons/profile-company/profile-company.component';
import { ProfileUserComponent } from './commons/profile-user/profile-user.component';
import { UserLoginDialogComponent } from './commons/user-login-dialog/user-login-dialog.component';
import { VideoSessionComponent } from './commons/video-session/video-session.component';
import {MatGridListModule} from "@angular/material/grid-list";
import { ApplyComponent } from './applicant-user/apply/apply.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import { EditApplicationComponent } from './applicant-user/edit-application/edit-application.component';
import { MatMomentDateModule, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';
import { DeleteApplicationDialogComponent } from './commons/delete-application-dialog/delete-application-dialog.component';

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
      ExploreJobsComponent,
      CreatePostComponent,
      CompanyPostComponent,
      DeletePostDialogComponent,
      ViewPostComponent,
      ProfileCompanyComponent,
      ProfileUserComponent,
      UserLoginDialogComponent,
      VideoSessionComponent,
      ApplyComponent,
      EditApplicationComponent,
      DeleteApplicationDialogComponent
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
    MatDialogModule,
    MatSelectModule,
    MatOptionModule,
    MatGridListModule,
    MatDatepickerModule,
    MatMomentDateModule
  ],
  providers: [
    LoginService,
    ConfirmAccountService,
    ProfileService,
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { useUtc: true } }
  ],
  bootstrap: [AppComponent],
  entryComponents: [DeleteDialogComponent]
})
export class AppModule { }
