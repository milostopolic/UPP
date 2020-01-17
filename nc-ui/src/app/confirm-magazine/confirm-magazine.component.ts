import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';
import { MagazineService } from '../services/magazine/magazine.service';

@Component({
  selector: 'app-confirm-magazine',
  templateUrl: './confirm-magazine.component.html',
  styleUrls: ['./confirm-magazine.component.css']
})
export class ConfirmMagazineComponent implements OnInit {

 
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  selectedValues: number[] = [];

  constructor(private userService: UserService, private repositoryService: RepositoryService, private activatedRoute: ActivatedRoute, private magazineService: MagazineService) {


    let x = repositoryService.getOneTask(this.activatedRoute.snapshot.params.taskId);



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
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if (property == "potvrdaAktivacija") {
        o.push({ fieldId: property, fieldValue: value[property] });
      }
    }
    for (var ff of this.formFields) {
      if (ff.id != "potvrdaAktivacija") {
        o.push({ fieldId: ff.id, fieldValue: ff.defaultValue });
      }
    }

    console.log(o);
    let x = this.magazineService.confirmMagazine(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert("Confirmed!")
      },
      err => {
        console.log("Error occured");
        console.log(err.error);
      }
    );
  }

}
