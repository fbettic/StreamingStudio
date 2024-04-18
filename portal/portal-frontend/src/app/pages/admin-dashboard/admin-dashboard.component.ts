import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { LoginService } from '../../services/auth/login.service';
import { AdministratorService } from '../../services/administrator/administrator.service';
import { AdvertiserFormComponent } from '../../components/forms/advertiser-form/advertiser-form.component';
import { ModalFormComponent } from '../../components/shared/modal-form/modal-form.component';
import { AdvertisersTableComponent } from '../../components/tables/advertisers-table/advertisers-table.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    AdvertisersTableComponent,
    AdvertiserFormComponent,
    ModalFormComponent
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss',
})
export class AdminDashboardComponent implements OnInit {
  private loginService: LoginService = inject(LoginService);
  private administratorService: AdministratorService =
    inject(AdministratorService);

  isLoggedIn: boolean = false;

  ngOnInit(): void {
    this.loginService.userLoggedIn.subscribe({
      next: (userLoggedIn) => {
        this.isLoggedIn = userLoggedIn;
      },
    });

  }
}
