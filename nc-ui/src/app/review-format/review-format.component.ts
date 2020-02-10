import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute } from '@angular/router';
import { WorkService } from '../services/work.service';

@Component({
  selector: 'app-review-format',
  templateUrl: './review-format.component.html',
  styleUrls: ['./review-format.component.css']
})
export class ReviewFormatComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  selectedValues: number[] = [];
  workId;
  url;
  constructor(private userService: UserService, private repositoryService: RepositoryService,
     private activatedRoute: ActivatedRoute, private workService : WorkService) {


    let x = workService.claimTask(this.activatedRoute.snapshot.params.taskId);



    x.subscribe(
      res => {
        console.log(res);

        this.workId = res.weed;
        //C:\Users\Topolic\Desktop\UPP\nc\src\main\resources\Cover_letter_milos_topolic.pdf
        this.workService.getPath(this.workId).subscribe( data => {
          console.log(data);
          this.url = data.value;
          var size = this.url.split("\\");
          this.url = this.url.split("\\")[size.length-1];
          console.log(this.url);

          this.url = "http://127.0.0.1:8887/" + this.url;
          window.open(this.url, "_blank");
        });


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
      if (property == "reviewFormFormat") {
        o.push({ fieldId: property, fieldValue: value[property] });
      }
    }
    // for (var ff of this.formFields) {
    //   if (ff.id != "checkFormWorkRelevant") {
    //     o.push({ fieldId: ff.id, fieldValue: ff.defaultValue });
    //   }
    // }

    console.log(o);
    let x = this.workService.postFormat(o, this.formFieldsDto.taskId);

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
