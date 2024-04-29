import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from '../../../services/auth/login.service';
import { ILoginResponse } from '../../../models/login-response.model';

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

  user: ILoginResponse | null = null

  constructor() {
    this._loginService.userLoggedIn.subscribe({
      next: (userLoggedIn) => {
        this.isLoggedIn = userLoggedIn;
      },
    });

    this._loginService.userData.subscribe({
      next: (data) => {
        this.user = data;
      },
    });
  }

  logout(): void {
    this._loginService.logout();
    this._router.navigateByUrl('/');
  }
}
