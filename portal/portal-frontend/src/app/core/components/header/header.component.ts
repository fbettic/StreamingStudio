import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ILoginResponse } from '../../models/login-response.model';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgbModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  private _loginService: LoginService = inject(LoginService);
  private _router: Router = inject(Router);
  isLoggedIn: boolean = false;

  user: ILoginResponse | null = null;
  homeLink: string = '';

  constructor() {
    this._loginService.userLoggedIn.subscribe({
      next: (userLoggedIn) => {
        this.isLoggedIn = userLoggedIn;
      },
    });

    this._loginService.userData.subscribe({
      next: (data) => {
        this.user = data;
        switch (this.user.role) {
          case 'SUBSCRIBER':
            this.homeLink = "/home"
            break;
          case 'ADMINISTRATOR':
            this.homeLink = "/admin"
            break;
          case 'ADVERTISIER':
            this.homeLink = "/advertiser"
            break;
          default:
            break;
        }
      },
    });
  }

  logout(): void {
    this._loginService.logout();
    this._router.navigateByUrl('/');
  }
}
