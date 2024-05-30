import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject, input } from '@angular/core';

import { IAdvertising } from '../../../../core/models/advertising.model';
import { Header } from '../../../../core/models/header.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { AdvertisingService } from '../../../../shared/services/advertising.service';
import { AdvertisingFormComponent } from '../../forms/advertising-form/advertising-form.component';

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
  modalTitle: string = $localize`New Advertising`;

  private advertisingServices: AdvertisingService = inject(AdvertisingService);
  private _list: IAdvertising[] = [];
  headers: Header[] = [
    { key: 'advertisingId', label: 'ID' },
    { key: 'advertiserId', label: $localize`Advertiser ID` },
    { key: 'referenceId', label: $localize`Reference ID` },
    { key: 'size', label: $localize`Size` },
    { key: 'priority', label: $localize`Priority` },
    { key: 'allPagesFee', label: $localize`All Pages` },
    { key: 'redirectUrl', label: 'URL' },
    { key: 'imageUrl', label: $localize`Img URL` },
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
        this.setItems(
          res.map((advertising) => {
            return {
              ...advertising,
              size: `${advertising.sizeType}-$${advertising.sizeFee}`,
              priority: `${advertising.priorityType}-$${advertising.priorityFee}`,
            };
          })
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
        complete: () => {
          this.getList();
        },
      });
  }

  showAddNewButton(): boolean {
    return this.advertiserId() !== 0;
  }

  onModalClose() {
    this.itemSelected = null;
  }

  filterAdvertisings(filter: string) {}

  refreshList(): void {
    this.modal.close();
    this.getList();
  }
}
