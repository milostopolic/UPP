import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AlertModule } from 'ngx-bootstrap';
import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';

import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { AuthInterceptor } from './auth/auth-interceptor';
import { ConfirmRegistrationComponent } from './confirm-registration/confirm-registration.component';
import { ConfirmReviewersComponent } from './confirm-reviewers/confirm-reviewers.component';
import { AdminTasksComponent } from './admin-tasks/admin-tasks.component';
import { NewMagazineComponent } from './new-magazine/new-magazine.component';
import { ChooseRevAndEdComponent } from './choose-rev-and-ed/choose-rev-and-ed.component';
import { ConfirmMagazineComponent } from './confirm-magazine/confirm-magazine.component';
import { LoginComponent } from './login/login.component';

const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  {
    path: "registrate",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "newMagazine",
    component: NewMagazineComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "confirmRegistration/:processId",
    component: ConfirmRegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "confirmReviewers/:taskId",
    component: ConfirmReviewersComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "confirmMagazine/:taskId",
    component: ConfirmMagazineComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "adminTasks",
    component: AdminTasksComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "chooseRevAndEd/:processId",
    component: ChooseRevAndEdComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [Notauthorized]
  }
]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    ConfirmRegistrationComponent,
    ConfirmReviewersComponent,
    AdminTasksComponent,
    NewMagazineComponent,
    ChooseRevAndEdComponent,
    ConfirmMagazineComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule,
    AlertModule.forRoot()
  ],
  
  providers:  [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
    Admin,
    Authorized,
    Notauthorized
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
