import { Component, ViewChild, inject } from '@angular/core';
import { AdvertiserFormComponent } from '../../forms/advertiser-form/advertiser-form.component';
import { TableComponent } from '../../shared/table/table.component';
import { ServiceType } from '../../../enums/service-type.enum';
import { IAdvertiser } from '../../../models/advertiser.model';
import { AdvertiserService } from '../../../services/advertiser.service';

import { CommonModule } from '@angular/common';
import { Header } from '../../../models/header.model';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';

@Component({
  selector: 'app-advertisers-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    AdvertiserFormComponent,
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
    { key: 'advertiserId', label: 'ID' },
    { key: 'companyName', label: 'Company Name' },
    { key: 'bussinesName', label: 'Bussines Name' },
    { key: 'serviceType', label: 'Service Type' },
    { key: 'apiUrl', label: 'API URL' },
  ];

  filteredList: IAdvertiser[] = [];
  searchQuery: string = '';
  itemSelected!: IAdvertiser | null;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._advertiserService.getAllAdvertisers().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._advertiserService.getAdvertisers ~ res:',
          res
        );
        const _list = res.map((item) => {
          {
            return { ...item, canEdit: this.isEditable(item) };
          }
        });
        this._list = _list;
        this.filteredList = _list;
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._advertiserService.getAdvertisers ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this._advertiserService.getAdvertisers ~ complete'
        );
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
        next: (res) => {
          console.log(
            '🚀 ~ TableComponent ~ this._advertiserService.deleteAdvertiser ~ res:',
            res
          );
        },
        error: (error) => {
          console.log(
            '🚀 ~ TableComponent ~ this._advertiserService.deleteAdvertiser ~ error:',
            error
          );
        },
        complete: () => {
          this.getList();
        },
      });
  }

  onModalClose() {
    console.log(
      '🚀 ~ AdvertisersTableComponent ~ onModalClose ~ onModalClose:'
    );

    this.itemSelected = null;
  }

  isEditable(advertiser: IAdvertiser): boolean {
    return advertiser.serviceType !== ServiceType.ACCOUNT;
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
    this.modal.close();
    this.getList();
  }

  filterAdvertisers(filter: string): void {
    console.log('🚀 ~ TableComponent ~ filterAdvertisers ~ filter:', filter);

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
