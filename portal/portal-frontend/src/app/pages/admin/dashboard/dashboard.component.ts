import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { AdvertisersTableComponent } from '../../../components/tables/advertisers-table/advertisers-table.component';
import { AdvertisingsTableComponent } from '../../../components/tables/advertisings-table/advertisings-table.component';
import { FeeTableComponent } from '../../../components/tables/fee-table/fee-table.component';
import { PlatformsTableComponent } from '../../../components/tables/platforms-table/platforms-table.component';
import { PrioritiesTableComponent } from '../../../components/tables/priorities-table/priorities-table.component';
import { SizesTableComponent } from '../../../components/tables/sizes-table/sizes-table.component';
import { TargetsTableComponent } from '../../../components/tables/targets-table/targets-table.component';
import { LoginService } from '../../../services/auth/login.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    AdvertisersTableComponent,
    AdvertisingsTableComponent,
    PlatformsTableComponent,
    CustomModalComponent,
    FeeTableComponent,
    SizesTableComponent,
    PrioritiesTableComponent,
    TargetsTableComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export default class DashboardComponent {
  private loginService: LoginService = inject(LoginService);

  isLoggedIn: boolean = false;

  constructor() {
    this.loginService.userLoggedIn.subscribe({
      next: (userLoggedIn) => {
        this.isLoggedIn = userLoggedIn;
      },
    });
  }
}
