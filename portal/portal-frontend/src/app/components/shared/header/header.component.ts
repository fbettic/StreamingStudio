import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from '../../../services/auth/login.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgbModule, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  private loginService: LoginService = inject(LoginService);
  isLoggedIn: boolean = false;

  ngOnInit(): void {
    this.loginService.userLoggedIn.subscribe({
      next: (userLoggedIn) => {
        this.isLoggedIn = userLoggedIn;
      },
    });
  }

  logout(): void{
    this.loginService.logout()
  }
}
