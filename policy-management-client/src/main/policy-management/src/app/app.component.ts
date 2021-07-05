import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from './_services/token-storage.service';

import { environment } from 'src/environments/environment';
import { AuthService } from './_services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showSuperAdminBoard = false;
  showModeratorBoard = false;
  username?: string;
  serverDown:boolean = false;

  environmentName = '';

  constructor(private router: Router, private tokenStorageService: TokenStorageService, private authService: AuthService) { 
    this.environmentName = environment.environmentName;
    console.log( `environment.environmentName ${environment.environmentName}`);
  }

  ngOnInit(): void {

    this.authService.test().subscribe(
      (data) => {
        console.log( `serverDown ${ this.serverDown}`);
        this.serverDown = false;
        this.router.navigate(['/login']);
      },
      (err) => {
        if (err.status == 0){ //or whatever condition you like to put
          this.serverDown = true;
          console.log( `serverDown ${ this.serverDown}`);
       //   this.router.navigate(['/error']);
      }
      }
    );

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ADMIN');
      this.showSuperAdminBoard = this.roles.includes('SUPERADMIN');
      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate(['/login']);
    window.location.reload();
  }
}
