import { query } from '@angular/animations';
import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { IAdvertiser } from '../../../models/advertiser.model';
import { AdministratorService } from '../../../services/administrator/administrator.service';
import { AdvertiserService } from '../../../services/advertiser/advertiser.service';
import { ModalFormComponent } from '../modal-form/modal-form.component';
import { AdvertiserFormComponent } from '../../forms/new-advertiser-form/advertiser-form.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ServiceType } from '../../../enums/service-type.enum';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule, ModalFormComponent, AdvertiserFormComponent],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss',
})
export class TableComponent implements OnInit {
  @ViewChild(ModalFormComponent) modal!: ModalFormComponent;

  private advertiserServices: AdvertiserService = inject(AdvertiserService);
  private list: IAdvertiser[] = [];

  filteredList: IAdvertiser[] = [];
  searchQuery: string = '';
  itemSelected!: IAdvertiser;

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
        this.list = res;
        this.filteredList = res;
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

  editValues(advertiser: IAdvertiser): void {
    if (this.isEditable(advertiser)) {
      this.modal.open();
      this.itemSelected = advertiser;
    }
  }

  deleteItem(id: number): void {
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
      complete: ()=>{
        this.getList()
      },
    });
    
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
