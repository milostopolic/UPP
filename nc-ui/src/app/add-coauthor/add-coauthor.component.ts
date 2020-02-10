
import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/users/user.service';
import { RepositoryService } from '../services/repository/repository.service';
import { MagazineService } from '../services/magazine/magazine.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/User';
import { AuthService } from '../auth/service/auth.service';
import { WorkService } from '../services/work.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-add-coauthor',
  templateUrl: './add-coauthor.component.html',
  styleUrls: ['./add-coauthor.component.css']
})
export class AddCoauthorComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private naucneOblasti = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private mozeSubmit: boolean = false;
  private selectedItems = [];
  dropdownSettings: IDropdownSettings;
  private dropDownList = [];
  selectedValues: number[] = [];
  next: boolean = false;
  user: User = new User();

  magz;

  fileUrl: string;
  fileToUpload: File;

  constructor(private userService: UserService, private repositoryService: RepositoryService,
    private magazineService: MagazineService, private router: Router, private authService: AuthService, private workService: WorkService
    , private activatedRoute: ActivatedRoute) {

    this.dropdownSettings = {
      singleSelection: true,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      allowSearchFilter: true,

    };

    

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
    // console.log(this.dropDownList);
    // this.dropDownList = [];
    // console.log(this.dropDownList);
    // this.repositoryService.getNaucneOblasti().subscribe(res => {
    //   let here = res;
    //   // console.log(res);
    //   here.forEach(element => {
    //     let prom = {item_id:element.id, item_text:element.naziv};
    //     console.log(prom);
    //     this.dropDownList.push(prom);
    //     console.log(this.dropDownList);
    //   });
    // });
  }

  selectedFile = null;
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
  }

  onSubmit(value, form) {
    let o = new Array();
   
    

      

      // if (this.selectedItems.length != 1) {
      //   alert("Molimo vas da odaberete jedan casopis");
      // } else {
        for (var property in value) {
          if (property != "workFormScientificArea" && property != "workFormPdf") {
            o.push({ fieldId: property, fieldValue: value[property] });
          } else if(property != "workFormPdf") {
            if (value[property] != "") {
              let all = [];
              value[property].forEach(element => {
                all.push(element.item_id);
              });
              o.push({ fieldId: property, enumi: all });
            }
  
          }
        }
  
        console.log(o);
  
        this.authService.getLogged().subscribe(logged => {
          this.user = logged;
          let x = this.workService.addCoauthor(o, this.formFieldsDto.taskId);
          x.subscribe(
            res => {
              console.log(res);
              this.next = true;
              alert("You registered successfully!");
            },
            err => {
              console.log("Error occured");
              console.log(err.error);
            }
          );
        });
      // }

    
    
    
  }

  onChange() {
    if (this.selectedValues.length >= 1) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }
  }

  goNext() {
    this.router.navigate(["chooseRevAndEd/" + this.processInstance]);
  }


  onItemSelect(item: any, a: any) {

    this.selectedItems.push(item);
    if (this.selectedItems.length == 1) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }

  }
  onSelectAll(items: any, a: any) {

    this.selectedItems.push(items);
    if (this.selectedItems.length == 1) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }

  }
  onItemDeSelect(item: any, a: any) {

    this.selectedItems.splice(item, 1);
    if (this.selectedItems.length == 1) {
      this.mozeSubmit = true;
    } else {
      this.mozeSubmit = false;
    }


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
