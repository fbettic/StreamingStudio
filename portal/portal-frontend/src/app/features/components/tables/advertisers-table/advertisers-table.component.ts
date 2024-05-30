import { Component, ViewChild, inject } from '@angular/core';
import { ServiceType } from '../../../../core/enums/service-type.enum';
import { IAdvertiser } from '../../../../core/models/advertiser.model';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { AdvertiserService } from '../../../../shared/services/advertiser.service';
import { AdvertiserFormComponent } from '../../forms/advertiser-form/advertiser-form.component';

import { CommonModule } from '@angular/common';
import { Header } from '../../../../core/models/header.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { AdvertisingFormComponent } from '../../forms/advertising-form/advertising-form.component';

@Component({
  selector: 'app-advertisers-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    AdvertiserFormComponent,
    AdvertisingFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './advertisers-table.component.html',
  styleUrl: './advertisers-table.component.scss',
})
export class AdvertisersTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private _advertiserService: AdvertiserService = inject(AdvertiserService);
  private _list: IAdvertiser[] = [];
  headers: Header[] = [
    { key: 'advertiserId', label: `ID` },
    { key: 'companyName', label: $localize`Company Name` },
    { key: 'bussinesName', label: $localize`Bussines Name` },
    { key: 'serviceType', label: $localize`Service Type` },
    { key: 'apiUrl', label: 'API URL' },
  ];

  filteredList: IAdvertiser[] = [];
  searchQuery: string = '';
  itemSelected!: IAdvertiser | null;
  modalTitle: string = $localize`New Advertiser`;
  addAdvertisingButtonTitle: string = $localize`New Ad`;
  advertisingMode: boolean = false;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._advertiserService.getAllAdvertisers().subscribe({
      next: (res) => {
        const _list = res.map((item) => {
          {
            return { ...item, canEdit: this.isEditable(item) };
          }
        });
        this._list = _list;
        this.filteredList = _list;
      },
    });
  }

  onEditItem(advertiser: IAdvertiser): void {
    if (this.isEditable(advertiser)) {
      this.modal.open();
      this.itemSelected = advertiser;
    }
  }

  onDeleteItem(advertiser: IAdvertiser): void {
    this._advertiserService
      .deleteAdvertiser(advertiser.advertiserId)
      .subscribe({
        complete: () => {
          this.getList();
        },
      });
  }

  onModalClose() {
    this.advertisingMode = false;
  }

  createAdvertising(advertiser: IAdvertiser) {
    this.advertisingMode = true;
    this.itemSelected = advertiser;
    this.modal.open();
  }

  isEditable(advertiser: IAdvertiser): boolean {
    return advertiser.serviceType !== ServiceType.ACCOUNT;
  }

  refreshList(): void {
    this.modal.close();
    this.advertisingMode = true;
    this.getList();
  }

  filterAdvertisers(filter: string): void {
    filter = filter.toLowerCase();
    if (filter == '') {
      this.filteredList = this._list;
      return;
    }

    this.filteredList = this._list.filter((advertiser) => {
      return (
        advertiser.agentFirstname.toLowerCase().includes(filter) ||
        advertiser.agentLastname.toLowerCase().includes(filter) ||
        advertiser.companyName.toLowerCase().includes(filter) ||
        advertiser.bussinesName.toLowerCase().includes(filter)
      );
    });
  }
}
