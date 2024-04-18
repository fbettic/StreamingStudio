import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { ModalFormComponent } from '../../shared/modal-form/modal-form.component';
import { TableComponent } from '../../shared/table/table.component';
import { IAdvertising } from '../../../models/advertising.model';
import { AdvertisingService } from '../../../services/advertising.service';
import { Header } from '../../../models/header.model';
import { AdvertisingFormComponent } from '../../forms/advertising-form/advertising-form.component';

@Component({
  selector: 'app-advertisings-table',
  standalone: true,
  imports: [CommonModule, TableComponent, AdvertisingFormComponent, ModalFormComponent],
  templateUrl: './advertisings-table.component.html',
  styleUrl: './advertisings-table.component.scss'
})
export class AdvertisingsTableComponent {
  @ViewChild(ModalFormComponent) modal!: ModalFormComponent;

  private advertisingServices: AdvertisingService = inject(AdvertisingService);
  private list: IAdvertising[] = [];
  headers: Header[] = [
    { key: 'companyName', label: 'Company Name' },
    { key: 'bussinesName', label: 'Bussines Name' },
    { key: 'serviceType', label: 'Service Type' },
    { key: 'apiUrl', label: 'API URL' },
  ];

  filteredList: IAdvertising[] = [];
  searchQuery: string = '';
  itemSelected!: IAdvertising | null

  constructor() {}

  ngOnInit(): void {
    this.getList();
  }

  getList(): void {
    this.advertisingServices.getAdvertising().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertisingServices.getAdvertising ~ res:',
          res
        );
        const list = res.map((item) => {
          {
            return { ...item, canEdit: true };
          }
        });
        this.list = list;
        this.filteredList = list;
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertisingServices.getAdvertising ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertisingServices.getAdvertising ~ complete'
        );
      },
    });
  }

  onEditItem(advertising: IAdvertising): void {
      this.modal.open();
      this.itemSelected = advertising;
  }

  onDeleteItem(id: number): void {
    this.advertisingServices.deleteAdvertising(id).subscribe({
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

  onModalClose(){
    console.log("🚀 ~ AdvertisingTableComponent ~ onModalClose ~ onModalClose:")
    this.itemSelected = null
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
    this.modal.close();
    this.getList();
  }

}
