import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatRadioModule } from '@angular/material/radio';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAccountComponent } from './create-account/create-account.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {LoginService} from "./service/login.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConfirmAccountComponent } from './confirm-account/confirm-account.component';
import { ResendConfirmationComponent } from './resend-confirmation/resend-confirmation.component';
import {MatFormField, MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {ConfirmAccountService} from "./service/confirm-account.service";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatCardContent, MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateAccountComponent,
    LoginComponent,
    LogoutComponent,
    ConfirmAccountComponent,
    ResendConfirmationComponent,
    HeaderComponent
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
