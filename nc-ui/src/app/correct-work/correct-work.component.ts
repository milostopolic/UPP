import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute, Router } from '@angular/router';
import { WorkService } from '../services/work.service';

@Component({
  selector: 'app-correct-work',
  templateUrl: './correct-work.component.html',
  styleUrls: ['./correct-work.component.css']
})
export class CorrectWorkComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  selectedValues: number[] = [];
  fileToUpload: File;
  fileUrl: string;
  constructor(private userService: UserService, private repositoryService: RepositoryService,
     private activatedRoute: ActivatedRoute, private workService : WorkService, private router : Router) {


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
   
    this.workService.uploadFile(this.fileToUpload).subscribe(response => {

      console.log(response);
      o.push({ fieldId: "correctingFormPdf", fieldValue: response });

      
  
        console.log(o);
  
        
          let x = this.workService.postCorrected(o, this.formFieldsDto.taskId);
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
        
    });
  }

  handleFileInput(file:FileList){
    this.fileToUpload =file.item(0);
    var reader = new FileReader();
    //console.log(this.fileToUpload)
    reader.onload=(event:any)=>{
      this.fileUrl =event.target.result;//ovo ne radi za pdf nesto
    }
    reader.readAsDataURL(this.fileToUpload);
    console.log("URL "+this.fileUrl);
    console.log("file "+this.fileToUpload.name);
}

}
