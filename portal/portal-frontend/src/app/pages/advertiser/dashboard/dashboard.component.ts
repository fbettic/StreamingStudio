import { Component, inject } from '@angular/core';
import { LoginService } from '../../../services/auth/login.service';
import { IAdvertiser } from '../../../models/advertiser.model';
import { AdvertiserService } from '../../../services/advertiser.service';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { AdvertiserFormComponent } from '../../../components/forms/advertiser-form/advertiser-form.component';
import { AdvertisingsTableComponent } from '../../../components/tables/advertisings-table/advertisings-table.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CustomModalComponent, AdvertiserFormComponent, AdvertisingsTableComponent],
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
        console.log("🚀 ~ DashboardComponent ~ constructor ~ data:", data)
        this.userId = data.id;
        this.getUserData();
      }
    });
  }

  getUserData() {
    console.log("🚀 ~ DashboardComponent ~ getUserData ~ this.userId:", this.userId)
    
    this._advertiserService.getAdvertiserById(this.userId).subscribe({
      
      next: (data) => {
        console.log("🚀 ~ DashboardComponent ~ this._advertiserService.getAdvertiserById ~ data:", data)
        this.user = data;
      },
    });
  }
}
