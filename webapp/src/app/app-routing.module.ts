import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {LogoutComponent} from "./logout/logout.component";
import {CreateAccountComponent} from "./create-account/create-account.component";
import {ConfirmAccountComponent} from "./confirm-account/confirm-account.component";

const routes: Routes = [

  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'create-account', component: CreateAccountComponent},
  {path: 'confirm-account', component: ConfirmAccountComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
