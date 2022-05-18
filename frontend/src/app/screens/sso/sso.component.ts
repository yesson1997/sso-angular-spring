import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-sso',
  templateUrl: './sso.component.html',
  styleUrls: ['./sso.component.css']
})
export class SsoComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private httpClient: HttpClient, private storage: TokenStorageService, private router: Router) {
    this.activatedRoute.queryParams.subscribe(params => {
          let token = params['token'];
          if (!token) return;
          this.storage.saveToken(token);
          this.getUser();
      });
  }

  ngOnInit(): void {

  }

  getUser() {
    this.httpClient.get("/api/users/me").subscribe(response => {
      console.log(response);
      this.storage.saveUser(response);
      this.router.navigate(["/home"])
    })
  }



}
