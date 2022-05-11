import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {LogoutComponent} from "./logout/logout.component";
import {CreateAccountComponent} from "./create-account/create-account.component";
import {ConfirmAccountComponent} from "./confirm-account/confirm-account.component";
import {ResendConfirmationComponent} from "./resend-confirmation/resend-confirmation.component";
import {ResetPasswordSendMailComponent} from "./reset-password-send-mail/reset-password-send-mail.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import {AccountSuccessfullyCreatedComponent} from "./account-successfully-created/account-successfully-created.component";
import {PasswordSuccessfullyResetComponent} from "./password-successfully-reset/password-successfully-reset.component";

const routes: Routes = [

  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'create-account', component: CreateAccountComponent},
  {path: 'confirm-account', component: ConfirmAccountComponent},
  {path: 'resend-confirmation', component: ResendConfirmationComponent},
  {path: 'reset-password-send-mail', component: ResetPasswordSendMailComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'account-successfully-created', component: AccountSuccessfullyCreatedComponent},
  {path: 'password-successfully-reset', component: PasswordSuccessfullyResetComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
