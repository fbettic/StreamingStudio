import { Component, ViewChild, inject } from '@angular/core';
import { ServiceType } from '../../../enums/service-type.enum';
import { Header } from '../../../models/header.model';
import { IStreamingPlatform } from '../../../models/streaming-platform.model';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';
import { StreamingPlatformService } from '../../../services/streaming-platform.service';
import { CommonModule } from '@angular/common';
import { PlatformFormComponent } from '../../forms/platform-form/platform-form.component';
import { TableComponent } from '../../shared/table/table.component';

@Component({
  selector: 'app-platforms-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    PlatformFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './platforms-table.component.html',
  styleUrl: './platforms-table.component.scss',
})
export class PlatformsTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private _streamingPlatformService: StreamingPlatformService = inject(
    StreamingPlatformService
  );
  private _list: IStreamingPlatform[] = [];
  headers: Header[] = [
    { key: 'platformName', label: 'Platform Name' },
    { key: 'loginFeeType', label: 'Login Fee Type' },
    { key: 'loginFee', label: 'Login Fee' },
    { key: 'signupFeeType', label: 'Signup Fee Type' },
    { key: 'signupFee', label: 'Signup Fee' },
    { key: 'serviceType', label: 'Service Type' },
    { key: 'apiUrl', label: 'API URL' },
    { key: 'authToken', label: 'Token' },
  ];

  filteredList: IStreamingPlatform[] = [];
  searchQuery: string = '';
  itemSelected!: IStreamingPlatform | null;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._streamingPlatformService.getAllStreamingPlatforms().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._streamingPlatformService.getStreamingPlatforms ~ res:',
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
          '🚀 ~ TableComponent ~ this._streamingPlatformService.getStreamingPlatforms ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this._streamingPlatformService.getStreamingPlatforms ~ complete'
        );
      },
    });
  }

  onEditItem(streamingPlatform: IStreamingPlatform): void {
    if (this.isEditable(streamingPlatform)) {
      this.modal.open();
      this.itemSelected = streamingPlatform;
    }
  }

  onDeleteItem(streamingPlatform: IStreamingPlatform): void {
    this._streamingPlatformService.deleteStreamingPlatformById(streamingPlatform.platformId).subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._streamingPlatformService.deleteStreamingPlatform ~ res:',
          res
        );
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._streamingPlatformService.deleteStreamingPlatform ~ error:',
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
      '🚀 ~ StreamingPlatformsTableComponent ~ onModalClose ~ onModalClose:'
    );

    this.itemSelected = null;
  }

  isEditable(streamingPlatform: IStreamingPlatform): boolean {
    return streamingPlatform.serviceType !== ServiceType.ACCOUNT;
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
    this.modal.close();
    this.getList();
  }

  filterStreamingPlatforms(filter: string): void {
    console.log(
      '🚀 ~ TableComponent ~ filterStreamingPlatforms ~ filter:',
      filter
    );

    filter = filter.toLowerCase();
    if (filter == '') {
      this.filteredList = this._list;
      return;
    }

    this.filteredList = this._list.filter((streamingPlatform) => {
      return (
        streamingPlatform.platformName.toLowerCase().includes(filter) ||
        streamingPlatform.serviceType.toLowerCase().includes(filter)
      );
    });
  }
}
