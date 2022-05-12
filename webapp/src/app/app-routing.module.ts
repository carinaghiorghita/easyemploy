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
import {DashboardComponent} from "./commons/dashboard/dashboard.component";

const routes: Routes = [

  {path: 'login', component: LoginComponent},
  {path: 'create-account', component: CreateAccountComponent},
  {path: 'confirm-account', component: ConfirmAccountComponent},
  {path: 'resend-confirmation', component: ResendConfirmationComponent},
  {path: 'reset-password-send-mail', component: ResetPasswordSendMailComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'account-successfully-created', component: AccountSuccessfullyCreatedComponent},
  {path: 'password-successfully-reset', component: PasswordSuccessfullyResetComponent},

  {path: 'dashboard', component: DashboardComponent},
  {path: 'profile', component: ProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
