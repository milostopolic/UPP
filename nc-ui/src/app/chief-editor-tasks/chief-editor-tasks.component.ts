import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RepositoryService } from '../services/repository/repository.service';
import { MagazineService } from '../services/magazine/magazine.service';
import { WorkService } from '../services/work.service';
import { User } from '../model/User';
import { AuthService } from '../auth/service/auth.service';

@Component({
  selector: 'app-chief-editor-tasks',
  templateUrl: './chief-editor-tasks.component.html',
  styleUrls: ['./chief-editor-tasks.component.css']
})
export class ChiefEditorTasksComponent implements OnInit {
  user : User = new User();
  private tasksC = [];
  constructor(private repositoryService: RepositoryService, private userService: UserService, private authService : AuthService,
     private activatedRoute: ActivatedRoute, private router: Router, private magazineService: MagazineService, private workService : WorkService) { 

      this.authService.getLogged().subscribe(logged => {
        this.user = logged;
        this.workService.getMyTasks(this.user.id).subscribe(data => {
          this.tasksC = data;
          console.log(this.tasksC);
        });
      });
     }

  ngOnInit() {
    
  }

  claimC(name, taskId) {
    console.log("brv");
    if(name=="Check Work Info") {
      this.router.navigate(["reviewBasicInfo/"+taskId]);
    }
    if(name=="Review PDF") {
      this.router.navigate(["reviewFormat/"+taskId]);
    }
    if(name=="Comment What Should Be Corrected") {
      this.router.navigate(["commentForCorrection/"+taskId]);
    }
    if(name=="Correct Work") {
      this.router.navigate(["correctWork/"+taskId]);
    }
    if(name=="Choose Reviewers") {
      this.router.navigate(["chooseReviewers/"+taskId]);
    }
    if(name=="Set Reviewing Deadline") {
      this.router.navigate(["setDeadline/"+taskId]);
    }
    if(name=="Reviewing Work" || name=="Reviewing Work By New Reviewer") {
      this.router.navigate(["reviewWork/"+taskId]);
    }
    if(name=="Making Final Decision") {
      this.router.navigate(["finalDecision/"+taskId]);
    }
    if(name=="Correcting The Work - Minor Changes") {
      this.router.navigate(["changesMinor/"+taskId]);
    }
    if(name=="Correcting The Work - Major Changes") {
      this.router.navigate(["changesMajor/"+taskId]);
    }
    if(name=="Choose New Reviewer") {
      this.router.navigate(["chooseNew/"+taskId]);
    }
    if(name=="Add Coauthor") {
      this.router.navigate(["addCoauthor/"+taskId]);
    }
    
  }

  

}
