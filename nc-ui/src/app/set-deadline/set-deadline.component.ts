import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';
import { WorkService } from '../services/work.service';

@Component({
  selector: 'app-set-deadline',
  templateUrl: './set-deadline.component.html',
  styleUrls: ['./set-deadline.component.css']
})
export class SetDeadlineComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  selectedValues: number[] = [];

  constructor(private userService: UserService, private repositoryService: RepositoryService,
     private activatedRoute: ActivatedRoute, private workService : WorkService) {


    let x = workService.claimTask(this.activatedRoute.snapshot.params.taskId);



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
      
        o.push({ fieldId: property, fieldValue: value[property] });
      
    }
    // for (var ff of this.formFields) {
    //   if (ff.id != "checkFormWorkRelevant") {
    //     o.push({ fieldId: ff.id, fieldValue: ff.defaultValue });
    //   }
    // }

    console.log(o);
    let x = this.workService.postDeadline(o, this.formFieldsDto.taskId);

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
