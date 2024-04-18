import { Component, ViewChild, inject } from '@angular/core';
import { AdvertiserFormComponent } from '../../forms/advertiser-form/advertiser-form.component';
import { TableComponent } from '../../shared/table/table.component';
import { ServiceType } from '../../../enums/service-type.enum';
import { IAdvertiser } from '../../../models/advertiser.model';
import { AdvertiserService } from '../../../services/advertiser/advertiser.service';
import { ModalFormComponent } from '../../shared/modal-form/modal-form.component';
import { CommonModule } from '@angular/common';
import { Header } from '../../../models/header.model';

@Component({
  selector: 'app-advertisers-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    AdvertiserFormComponent,
    ModalFormComponent,
    TableComponent,
  ],
  templateUrl: './advertisers-table.component.html',
  styleUrl: './advertisers-table.component.scss',
})
export class AdvertisersTableComponent {
  @ViewChild(ModalFormComponent) modal!: ModalFormComponent;

  private advertiserServices: AdvertiserService = inject(AdvertiserService);
  private list: IAdvertiser[] = [];
  headers: Header[] = [
    { key: 'companyName', label: 'Company Name' },
    { key: 'bussinesName', label: 'Bussines Name' },
    { key: 'serviceType', label: 'Service Type' },
    { key: 'apiUrl', label: 'API URL' },
  ];

  filteredList: IAdvertiser[] = [];
  searchQuery: string = '';
  itemSelected!: IAdvertiser | null

  constructor() {}

  ngOnInit(): void {
    this.getList();
  }

  getList(): void {
    this.advertiserServices.getAdvertisers().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertiserServices.getAdvertisers ~ res:',
          res
        );
        const list = res.map((item) => {
          {
            return { ...item, canEdit: this.isEditable(item) };
          }
        });
        this.list = list;
        this.filteredList = list;
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertiserServices.getAdvertisers ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertiserServices.getAdvertisers ~ complete'
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

  onDeleteItem(id: number): void {
    this.advertiserServices.deleteAdvertiser(id).subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertiserServices.deleteAdvertiser ~ res:',
          res
        );
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this.advertiserServices.deleteAdvertiser ~ error:',
          error
        );
      },
      complete: () => {
        this.getList();
      },
    });
  }

  onModalClose(){
    console.log("🚀 ~ AdvertisersTableComponent ~ onModalClose ~ onModalClose:")
    
    this.itemSelected = null
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
      this.filteredList = this.list;
      return;
    }

    this.filteredList = this.list.filter((advertiser) => {
      return (
        advertiser.agentFirstname.toLowerCase().includes(filter) ||
        advertiser.agentLastname.toLowerCase().includes(filter) ||
        advertiser.companyName.toLowerCase().includes(filter) ||
        advertiser.bussinesName.toLowerCase().includes(filter)
      );
    });
  }
}
