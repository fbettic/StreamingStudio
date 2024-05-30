import { Component, inject } from '@angular/core';
import { ILoginResponse } from '../../../../core/models/login-response.model';
import { LoginService } from '../../../../core/services/login.service';
import { PlatformListComponent } from '../../../components/platform-list/platform-list.component';

@Component({
  selector: 'app-platform-management',
  standalone: true,
  imports: [PlatformListComponent],
  templateUrl: './platform-management.component.html',
  styleUrl: './platform-management.component.scss',
})
export default class PlatformManagementComponent {
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
