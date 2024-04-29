import { Component, inject } from '@angular/core';
import { PlatformListComponent } from '../../../components/lists/platform-list/platform-list.component';
import { LoginService } from '../../../services/auth/login.service';
import { ILoginRequest } from '../../../models/login-request.model';
import { ILoginResponse } from '../../../models/login-response.model';

@Component({
  selector: 'app-platform-management',
  standalone: true,
  imports: [PlatformListComponent],
  templateUrl: './platform-management.component.html',
  styleUrl: './platform-management.component.scss',
})
export class PlatformManagementComponent {
  private _loginService: LoginService = inject(LoginService);

  user: ILoginResponse | null = null;

  constructor() {
    this._loginService.userData.subscribe({
      next: (data) => {
        this.user = data;
      },
    });
    
  }
}
