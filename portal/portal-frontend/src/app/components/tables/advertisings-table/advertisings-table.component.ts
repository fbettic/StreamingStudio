import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject, input } from '@angular/core';

import { TableComponent } from '../../shared/table/table.component';
import { IAdvertising } from '../../../models/advertising.model';
import { AdvertisingService } from '../../../services/advertising.service';
import { Header } from '../../../models/header.model';
import { AdvertisingFormComponent } from '../../forms/advertising-form/advertising-form.component';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';

@Component({
  selector: 'app-advertisings-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    AdvertisingFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './advertisings-table.component.html',
  styleUrl: './advertisings-table.component.scss',
})
export class AdvertisingsTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private advertisingServices: AdvertisingService = inject(AdvertisingService);
  private _list: IAdvertising[] = [];
  headers: Header[] = [
    { key: 'advertisingId', label: 'ID' },
    { key: 'advertiserId', label: 'Advertiser ID' },
    { key: 'bannerId', label: 'Banner ID' },
    { key: 'size', label: 'Size' },
    { key: 'priority', label: 'Priority' },
    { key: 'allPagesFee', label: 'All Pages' },
    { key: 'redirectUrl', label: 'URL' },
    { key: 'imageUrl', label: 'Img URL' },
  ];

  filteredList: IAdvertising[] = [];
  searchQuery: string = '';
  itemSelected!: IAdvertising | null;

  advertiserId = input(0);

  constructor() {
    this.getList();
  }

  getList(): void {
    this.advertisingServices.getAllAdvertisings().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertisingServices.getAdvertising ~ res:',
          res
        );
        this.setItems(res.map((advertising)=>{
          return {
            ...advertising,
            size: `${advertising.sizeType}-$${advertising.sizeFee}`,
            priority: `${advertising.priorityType}-$${advertising.priorityFee}`
          }
        }));
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertisingServices.getAdvertising ~ error:',
          error
        );
      },
    });
  }

  setItems(advertisings: IAdvertising[]) {
    const list = advertisings.map((item) => {
      {
        return { ...item, canEdit: true };
      }
    });

    this._list = list;
    this.filteredList = list;
  }

  onEditItem(advertising: IAdvertising): void {
    this.modal.open();
    this.itemSelected = advertising;
  }

  onDeleteItem(advertising: IAdvertising): void {
    this.advertisingServices
      .deleteAdvertising(advertising.advertisingId)
      .subscribe({
        next: (res) => {
          console.log(
            '🚀 ~ TableComponent ~ this.advertisingServices.deleteAdvertising ~ res:',
            res
          );
        },
        error: (error) => {
          console.log(
            '🚀 ~ TableComponent ~ this.advertisingServices.deleteAdvertising ~ error:',
            error
          );
        },
        complete: () => {
          this.getList();
        },
      });
  }

  showAddNewButton(): boolean {
    return this.advertiserId() !== 0;
  }

  onModalClose() {
    console.log(
      '🚀 ~ AdvertisingTableComponent ~ onModalClose ~ onModalClose:'
    );
    this.itemSelected = null;
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
    this.modal.close();
    this.getList();
  }
}
