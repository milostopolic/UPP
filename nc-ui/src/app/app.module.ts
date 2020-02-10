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
import { NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
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
import { ChooseMagazineComponent } from './choose-magazine/choose-magazine.component';
import { NewWorkComponent } from './new-work/new-work.component';
import { ChiefEditorTasksComponent } from './chief-editor-tasks/chief-editor-tasks.component';
import { ReviewBasicInfoComponent } from './review-basic-info/review-basic-info.component';
import { ReviewFormatComponent } from './review-format/review-format.component';
import { CommentForCorrectionComponent } from './comment-for-correction/comment-for-correction.component';
import { CorrectWorkComponent } from './correct-work/correct-work.component';
import { ChooseReviewersComponent } from './choose-reviewers/choose-reviewers.component';
import { SetDeadlineComponent } from './set-deadline/set-deadline.component';
import { ReviewWorkComponent } from './review-work/review-work.component';
import { FinalDecisionComponent } from './final-decision/final-decision.component';
import { ChangesMinorComponent } from './changes-minor/changes-minor.component';
import { ChangesMajorComponent } from './changes-major/changes-major.component';
import { ChooseNewComponent } from './choose-new/choose-new.component';

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
  },
  {
    path: "chooseMagazine",
    component: ChooseMagazineComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "newWork/:taskId",
    component: NewWorkComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "chiefEditorTasks",
    component: ChiefEditorTasksComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "reviewBasicInfo/:taskId",
    component: ReviewBasicInfoComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "reviewFormat/:taskId",
    component: ReviewFormatComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "commentForCorrection/:taskId",
    component: CommentForCorrectionComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "correctWork/:taskId",
    component: CorrectWorkComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "chooseReviewers/:taskId",
    component: ChooseReviewersComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "setDeadline/:taskId",
    component: SetDeadlineComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "reviewWork/:taskId",
    component: ReviewWorkComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "finalDecision/:taskId",
    component: FinalDecisionComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "changesMinor/:taskId",
    component: ChangesMinorComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "changesMajor/:taskId",
    component: ChangesMajorComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "chooseNew/:taskId",
    component: ChooseNewComponent,
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
    LoginComponent,
    ChooseMagazineComponent,
    NewWorkComponent,
    ChiefEditorTasksComponent,
    ReviewBasicInfoComponent,
    ReviewFormatComponent,
    CommentForCorrectionComponent,
    CorrectWorkComponent,
    ChooseReviewersComponent,
    SetDeadlineComponent,
    ReviewWorkComponent,
    FinalDecisionComponent,
    ChangesMinorComponent,
    ChangesMajorComponent,
    ChooseNewComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule,
    AlertModule.forRoot(),
    NgMultiSelectDropDownModule.forRoot()
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
