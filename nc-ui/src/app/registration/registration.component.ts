import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  
  private formFieldsDto = null;
  private formFields = [];
  private naucneOblasti = [];
  private processInstance = "";
  private enumValues = [];
  private mozeSubmit: boolean = false;
  selectedValues: number[] = [];

  constructor(private userService: UserService, private repositoryService: RepositoryService) {

    let x = repositoryService.startProcess();

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
    let x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert("You registered successfully!")
      },
      err => {
        console.log("Error occured");
        console.log(err.error);
      }
    );
  }

  onChange() {
    if (this.selectedValues.length >= 2) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }
  }



  

}
