import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) { }

  startProcess() : Observable<any>{
    return this.httpClient.get('http://localhost:8080/casopis/get');
  }

  postMagazine(user, taskId, uid) {
    return this.httpClient.post("http://localhost:8080/casopis/post/".concat(taskId)+"/"+uid, user) as Observable<any>;
  }

  getRevsAndEditors(processId) : Observable<any> {
    return this.httpClient.get("http://localhost:8080/casopis/getRevsAndEditors/" + processId);
  }

  getMagRevs(c) : Observable<any> {
    return this.httpClient.get("http://localhost:8080/casopis/getMagazineReviewers/" + c);
  }

  getMagEds(c) : Observable<any> {
    return this.httpClient.get("http://localhost:8080/casopis/getMagazineEditors/" + c);
  }

  postChosenRE(user, taskId) {
      return this.httpClient.post("http://localhost:8080/casopis/postRevsAndEditors/".concat(taskId), user) as Observable<any>;
  }

  getAdminTasks() : Observable<any> {
    return this.httpClient.get('http://localhost:8080/casopis/getAdminTasks');
  }
 
  getOneTask(taskId) : Observable<any> {
    return this.httpClient.get('http://localhost:8080/casopis/getTask/' + taskId);
  }
   
  confirmMagazine(user, taskId) {
    return this.httpClient.post("http://localhost:8080/casopis/postMagConfirmation/".concat(taskId), user) as Observable<any>;
  }

  getAll() : Observable<any> {
    return this.httpClient.get('http://localhost:8080/casopis/getAllMagazines/');
  }

}
