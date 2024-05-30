import { Component, inject } from '@angular/core';
import { IAdvertiser } from '../../../../core/models/advertiser.model';
import { LoginService } from '../../../../core/services/login.service';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { AdvertiserService } from '../../../../shared/services/advertiser.service';
import { AdvertiserFormComponent } from '../../../components/forms/advertiser-form/advertiser-form.component';
import { AdvertisingsTableComponent } from '../../../components/tables/advertisings-table/advertisings-table.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CustomModalComponent,
    AdvertiserFormComponent,
    AdvertisingsTableComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export default class DashboardComponent {
  private _loginService: LoginService = inject(LoginService);
  private _advertiserService: AdvertiserService = inject(AdvertiserService);
  userId = 0;
  user: IAdvertiser | null = null;

  constructor() {
    this._loginService.userData.subscribe({
      next: (data) => {
        this.userId = data.id;
        this.getUserData();
      },
    });
  }

  getUserData() {
    this._advertiserService.getAdvertiserById(this.userId).subscribe({
      next: (data) => {
        this.user = data;
      },
    });
  }
}
