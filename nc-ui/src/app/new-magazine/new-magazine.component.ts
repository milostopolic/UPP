import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { MagazineService } from '../services/magazine/magazine.service';
import { Router } from '@angular/router';
import { AuthService } from '../auth/service/auth.service';
import { User } from '../model/User';

@Component({
  selector: 'app-new-magazine',
  templateUrl: './new-magazine.component.html',
  styleUrls: ['./new-magazine.component.css']
})
export class NewMagazineComponent implements OnInit {


  private formFieldsDto = null;
  private formFields = [];
  private naucneOblasti = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private mozeSubmit: boolean = false;
  selectedValues: number[] = [];
  next: boolean = false;
  user : User = new User();

  constructor(private userService: UserService, private repositoryService: RepositoryService,
     private magazineService: MagazineService, private router: Router, private authService: AuthService) {

    let x = magazineService.startProcess();

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach((field) => {

          if (field.type.name == 'enum') {
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured");
      }
    );


  }

  ngOnInit() {
    this.repositoryService.getNaucneOblasti().subscribe(data => {
      console.log(data);
      this.naucneOblasti = data;
      console.log(this.naucneOblasti);
    });
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({ fieldId: property, fieldValue: value[property] });
    }

    let niz: string = "";
    for (var no of this.selectedValues) {
      niz = niz + no + ",";
    }
    niz = niz.substr(0, niz.length - 1);
    o.push({ fieldId: "naucneOblasti", fieldValue: niz });

    console.log(o);

    this.authService.getLogged().subscribe(logged => {
      this.user = logged;
      let x = this.magazineService.postMagazine(o, this.formFieldsDto.taskId, this.user.id);


      

      x.subscribe(
        res => {
          console.log(res);
          this.next = true;
          alert("You registered successfully!")
        },
        err => {
          console.log("Error occured");
          console.log(err.error);
        }
      );
    });
  }

  onChange() {
      if(this.selectedValues.length >= 1) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }
  }

  goNext() {
    this.router.navigate(["chooseRevAndEd/" + this.processInstance]);
  }


}
