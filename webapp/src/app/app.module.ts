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
import { ProfileComponent } from './commons/profile/profile.component';

@NgModule({
  declarations: [
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
    ProfileComponent
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
    MatIconModule
  ],
  providers: [
    LoginService,
    ConfirmAccountService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
