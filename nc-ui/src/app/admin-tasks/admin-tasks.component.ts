import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RepositoryService } from '../services/repository/repository.service';
import { MagazineService } from '../services/magazine/magazine.service';

@Component({
  selector: 'app-admin-tasks',
  templateUrl: './admin-tasks.component.html',
  styleUrls: ['./admin-tasks.component.css']
})
export class AdminTasksComponent implements OnInit {

  private tasksR = [];
  private tasksC = [];

  constructor(private repositoryService: RepositoryService, private userService: UserService, private activatedRoute: ActivatedRoute, private router: Router, private magazineService: MagazineService) { }

  ngOnInit() {
    this.repositoryService.getAdminTasks().subscribe(data => {
      this.tasksR = data;
      console.log(this.tasksR);
    });
    this.magazineService.getAdminTasks().subscribe(data => {
      this.tasksC = data;
      console.log(this.tasksC);
    });
  }

  claimR(taskId) {
    console.log("brv");
    this.router.navigate(["confirmReviewers/"+taskId]);
  }

  claimC(taskId) {
    console.log("brv");
    this.router.navigate(["confirmMagazine/"+taskId]);
  }

}
