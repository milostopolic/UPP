import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/service/auth.service';
import { LogInRequest } from '../model/LogInRequest';
import { JWTAuth } from '../model/Jwt-Auth';
import { TokenStorageService } from '../auth/token-storage/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  validEmail = false;
  errorMessage = '';
  private loginInfo: LogInRequest;
  private jwtauth: JWTAuth;
  private element;

  constructor(private tokenStorage: TokenStorageService, private router: Router,
    private authService: AuthService) { }

  register() {
    this.router.navigate(['register']);
  }

  checkEmail() {
    this.authService.checkEmail(this.form.username).subscribe(data => {
      this.validEmail = true;
      this.isLoginFailed = false;


    }, error => {
      this.errorMessage = error.error.errorMessage;
      this.isLoginFailed = true;
    })
  }

  onSubmit() {
    console.log(this.form);
    this.loginInfo = new LogInRequest(
      this.form.username, // email zapravo 
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        console.table(data);
        this.jwtauth = data;
        this.tokenStorage.saveToken(data.accessToken);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.router.navigate(['/']);        
      },
      error => {
        this.errorMessage = "Wrong password, please try again."
        this.isLoginFailed = true;
      }
    );
  }

  ngOnInit() {


  }

}