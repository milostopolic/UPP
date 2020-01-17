import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { MagazineService } from '../services/magazine/magazine.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-choose-rev-and-ed',
  templateUrl: './choose-rev-and-ed.component.html',
  styleUrls: ['./choose-rev-and-ed.component.css']
})
export class ChooseRevAndEdComponent implements OnInit {

  
  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private mozeSubmit: boolean = false;
  selectedValues: number[] = [];
  selectedEds: number[] = [];
  selectedRevs: number[] = [];

  revs;
  eds;


  constructor(private userService: UserService, private repositoryService: RepositoryService, private magazineService: MagazineService, private activatedRoute: ActivatedRoute, private router: Router) {

    let x = magazineService.getRevsAndEditors(this.activatedRoute.snapshot.params.processId);

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
    this.magazineService.getMagEds(this.activatedRoute.snapshot.params.processId).subscribe(data => {
      console.log(data);
      this.eds = data;
    });

    this.magazineService.getMagRevs(this.activatedRoute.snapshot.params.processId).subscribe(data => {
      console.log(data);
      this.revs = data;
    });
  }

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
    }

    let nizE: string = "";
    if (this.selectedEds.length > 0) {
      for (var no of this.selectedEds) {
        nizE = nizE + no + ",";
      }
      nizE = nizE.substr(0, nizE.length - 1);
    }
    o.push({ fieldId: "urednici", fieldValue: nizE });



    let nizR: string = "";
    for (var no of this.selectedRevs) {
      nizR = nizR + no + ",";
    }
    nizR = nizR.substr(0, nizR.length - 1);
    o.push({ fieldId: "recenzenti", fieldValue: nizR });



    console.log(o);
    let x = this.magazineService.postChosenRE(o, this.formFieldsDto.taskId);

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
    if (this.selectedRevs.length >= 2) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }
  }

  

}
