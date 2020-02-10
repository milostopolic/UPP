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
  selector: 'app-choose-magazine',
  templateUrl: './choose-magazine.component.html',
  styleUrls: ['./choose-magazine.component.css']
})
export class ChooseMagazineComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private naucneOblasti = [];
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private mozeSubmit: boolean = false;
  private selectedItems = []; 
  dropdownSettings:IDropdownSettings;
  private dropDownList = [];
  selectedValues: number[] = [];
  next: boolean = false;
  user : User = new User();

  magz;

  constructor(private userService: UserService, private repositoryService: RepositoryService,
     private magazineService: MagazineService, private router: Router, private authService: AuthService, private workService : WorkService) {

      this.dropdownSettings = {
        singleSelection: true,
        idField: 'item_id',
        textField: 'item_text',
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        allowSearchFilter: true,
  
      };

      this.authService.getLogged().subscribe(logged => {
        this.user = logged;

        let x = workService.startProcess(this.user.id);

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
      });


  }

  ngOnInit() {
    this.magazineService.getAll().subscribe(res => {
      let here = res;
      console.log(res);
      here.forEach(element => {
        let prom = {item_id:element.id, item_text:element.naziv};
        this.dropDownList.push(prom);
      });
    });
  }

  onSubmit(value, form) {
    // if(this.selectedItems.length != 1) {
    //   alert("Molimo vas da odaberete jedan casopis");
    // } else {
      let o = new Array();
      for (var property in value) {
        console.log(property);
        console.log(value[property]);
        // o.push({ fieldId: property, fieldValue: value[property] });

        if(value[property] != ""){
          let all = [];
            value[property].forEach(element => {
                all.push(element.item_id);
            });
          o.push({fieldId: property, enumi: all});
        }
      }

      
      

      console.log(o);

      
        let x = this.workService.postChosenMagazine(o, this.formFieldsDto.taskId, this.user.id);


        

        x.subscribe(
          res => {
            console.log(res);
            this.next = true;
            alert("You registered successfully!")
            this.router.navigate(["/newWork/" + this.processInstance]);
          },
          err => {
            console.log("Error occured");
            console.log(err.error);
          }
        );
      
    // }
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


  onItemSelect(item: any, a: any) {
   
      this.selectedItems.push(item);
      if(this.selectedItems.length == 1) {
        this.mozeSubmit= true;
      } else {
        this.mozeSubmit= false;
      }
    
  }
  onSelectAll(items: any, a: any) {
    
      this.selectedItems.push(items);
      if(this.selectedItems.length == 1) {
        this.mozeSubmit= true;
      } else {
        this.mozeSubmit= false;
      }
    
  }
  onItemDeSelect(item: any, a: any) {
    
      this.selectedItems.splice(item,1);
      if(this.selectedItems.length == 1) {
        this.mozeSubmit= true;
      } else {
        this.mozeSubmit= false;
      }
    
    
  }

}
