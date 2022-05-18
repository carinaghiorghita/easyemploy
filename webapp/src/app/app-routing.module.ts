import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./unauthenticated-user/login/login.component";
import {CreateAccountComponent} from "./unauthenticated-user/create-account/create-account.component";
import {ConfirmAccountComponent} from "./unauthenticated-user/confirm-account/confirm-account.component";
import {ResendConfirmationComponent} from "./unauthenticated-user/resend-confirmation/resend-confirmation.component";
import {ResetPasswordSendMailComponent} from "./unauthenticated-user/reset-password-send-mail/reset-password-send-mail.component";
import {ResetPasswordComponent} from "./unauthenticated-user/reset-password/reset-password.component";
import {AccountSuccessfullyCreatedComponent} from "./unauthenticated-user/account-successfully-created/account-successfully-created.component";
import {PasswordSuccessfullyResetComponent} from "./unauthenticated-user/password-successfully-reset/password-successfully-reset.component";
import {ProfileComponent} from "./commons/profile/profile.component";
import {DashboardUserComponent} from "./applicant-user/dashboard-user/dashboard-user.component";
import {DashboardCompanyComponent} from "./company-user/dashboard-company/dashboard-company.component";
import {ExplorePeopleComponent} from "./commons/explore-people/explore-people.component";
import {ExploreCompaniesComponent} from "./commons/explore-companies/explore-companies.component";
import {ExploreJobsComponent} from "./commons/explore-jobs/explore-jobs.component";
import {CreatePostComponent} from "./company-user/create-post/create-post.component";

const routes: Routes = [

  //unauth
  {path: 'login', component: LoginComponent},
  {path: 'create-account', component: CreateAccountComponent},
  {path: 'confirm-account', component: ConfirmAccountComponent},
  {path: 'resend-confirmation', component: ResendConfirmationComponent},
  {path: 'reset-password-send-mail', component: ResetPasswordSendMailComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'account-successfully-created', component: AccountSuccessfullyCreatedComponent},
  {path: 'password-successfully-reset', component: PasswordSuccessfullyResetComponent},

  //commons
  {path: 'profile', component: ProfileComponent},
  {path: 'explore-people', component: ExplorePeopleComponent},
  {path: 'explore-companies', component: ExploreCompaniesComponent},
  {path: 'explore-jobs', component: ExploreJobsComponent},

  //user
  {path: 'dashboard-user', component: DashboardUserComponent},

  //company
  {path: 'dashboard-company', component: DashboardCompanyComponent},
  {path: 'new-post', component: CreatePostComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
