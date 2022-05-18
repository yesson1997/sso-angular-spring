import { TokenStorageService } from './../../service/token-storage.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  constructor(private tokenStorage: TokenStorageService,
    private router: Router,
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
    ) { }

  ngOnInit(): void {
    this.checkLoginStatus();
    this.form = this.buildLoginForm();
  }

  private checkLoginStatus() {
    console.log(this.tokenStorage.getUser());

    if (this.tokenStorage.getUser()) {
      this.navigateTo(['/home'])
    } else {
      this.navigateTo(['/login'])
    }
  }

  private navigateTo(pageUrl: string[]) {
    this.router.navigate(pageUrl);
  }

  private buildLoginForm(): FormGroup {
    return this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() { return this.form.controls; }

  onLogin() {
    if (this.form.invalid) {
      return;
    }

    this.httpClient.post("/api/users/signin", {
      username: this.f.username.value,
      password: this.f.password.value
    }).subscribe((response) => {
      console.log(response);
      this.onLoginSuccessful(response);
    })
  }

  onSSOLogin() {
    this.httpClient.get(`/api/users/auth_url`).subscribe(response => {
      let authUrl = (response as any).authUrl
      window.location.assign(authUrl);

    })
  }

  onLoginSuccessful(response: any) {
    this.saveUserInfo(response);
    this.navigateTo(['/home'])
  }

  saveUserInfo(response: any) {
    let user = {
      username: response.username,
      email: response.email,
      userRoles: response.userRoles
    };

    let token = response.token;
    this.tokenStorage.saveUser(user);
    this.tokenStorage.saveToken(token);
  }

}
