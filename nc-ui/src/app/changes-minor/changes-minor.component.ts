import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { ActivatedRoute, Router } from '@angular/router';
import { WorkService } from '../services/work.service';

@Component({
  selector: 'app-changes-minor',
  templateUrl: './changes-minor.component.html',
  styleUrls: ['./changes-minor.component.css']
})
export class ChangesMinorComponent implements OnInit {

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
      o.push({ fieldId: "minorFormPdf", fieldValue: response });

      // if (this.selectedItems.length != 1) {
      //   alert("Molimo vas da odaberete jedan casopis");
      // } else {
        for (var property in value) {
          if (property != "minorFormPdf") {
            o.push({ fieldId: property, fieldValue: value[property] });
          }
        }
  
        console.log(o);
  
        
          let x = this.workService.postMinor(o, this.formFieldsDto.taskId);
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
        
      // }

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

