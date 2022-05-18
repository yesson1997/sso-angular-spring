import { TokenStorageService } from './../../service/token-storage.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private storage: TokenStorageService, private router: Router,) { }

  user: any;

  ngOnInit(): void {
    this.user = this.storage.getUser();
  }

  onSignout() {
    this.storage.signout();
    this.navigateTo(['/login'])
  }

  private navigateTo(pageUrl: string[]) {
    this.router.navigate(pageUrl);
  }

}
