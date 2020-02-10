import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  constructor(private httpClient: HttpClient) { }

  startProcess(id) : Observable<any>{
    return this.httpClient.get('http://localhost:8080/work/get/' + id);
  }

  postChosenMagazine(user, taskId, uid) {
    return this.httpClient.post("http://localhost:8080/work/post/".concat(taskId)+"/"+uid, user) as Observable<any>;
  }

  postBasicInfo(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postWorkBasicInfo/".concat(taskId), user) as Observable<any>;
  }

  postCorrected(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postCorrected/".concat(taskId), user) as Observable<any>;
  }

  postMinor(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postMinor/".concat(taskId), user) as Observable<any>;
  }

  postMajor(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postMajor/".concat(taskId), user) as Observable<any>;
  }

  getOneTask(taskId) : Observable<any> {
    return this.httpClient.get('http://localhost:8080/work/getNextUserTask/' + taskId);
  }

  // uploadFile(taskId) : Observable<any> {
  //   return this.httpClient.post('http://localhost:8080/work/uploadPDF', taskId);
  // }

  uploadFile( fileToUpload: File) {
    const formData: FormData = new FormData();  
    formData.append("File", fileToUpload);
    return this.httpClient.post('http://localhost:8080/work/uploadPDF/', formData,{ responseType: 'text'});
  }

  getChiefsTasks() : Observable<any> {
    return this.httpClient.get('http://localhost:8080/work/getChiefEditorsTasks');
  }

  getMyTasks(uid) : Observable<any> {
    return this.httpClient.get('http://localhost:8080/work/getMyTasks/' + uid);
  }

  claimTask(taskId) : Observable<any> {
    return this.httpClient.get('http://localhost:8080/work/getTask/' + taskId);
  }

  postRelevance(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postRelevance/".concat(taskId), user) as Observable<any>;
  }

  postFormat(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postFormat/".concat(taskId), user) as Observable<any>;
  }

  postComment(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postComment/".concat(taskId), user) as Observable<any>;
  }

  postYourReview(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postYourReview/".concat(taskId), user) as Observable<any>;
  }

  getPath(wid) : Observable<any> {
    return this.httpClient.get('http://localhost:8080/work/pdfname/' + wid);
  }

  postChosenReviewers(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postChosenReviewers/".concat(taskId), user) as Observable<any>;
  }

  postNewRev(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postNewRev/".concat(taskId), user) as Observable<any>;
  }

  addCoauthor(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postCoauthor/".concat(taskId), user) as Observable<any>;
  }

  postDeadline(user, taskId) {
    return this.httpClient.post("http://localhost:8080/work/postDeadline/".concat(taskId), user) as Observable<any>;
  }

}
