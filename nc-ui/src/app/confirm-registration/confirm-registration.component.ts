import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirm-registration',
  templateUrl: './confirm-registration.component.html',
  styleUrls: ['./confirm-registration.component.css']
})
export class ConfirmRegistrationComponent implements OnInit {

  constructor(private userService: UserService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.userService.confirmAccount(this.activatedRoute.snapshot.params.processId).subscribe();
  }

}
