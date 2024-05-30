import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { ServiceType } from '../../../../core/enums/service-type.enum';
import { Header } from '../../../../core/models/header.model';
import { IStreamingPlatform } from '../../../../core/models/streaming-platform.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { StreamingPlatformService } from '../../../../shared/services/streaming-platform.service';
import { PlatformFormComponent } from '../../forms/platform-form/platform-form.component';

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
    { key: 'platformName', label: $localize`Platform Name` },
    { key: 'loginFeeType', label: $localize`Login Fee Type` },
    { key: 'loginFee', label: $localize`Login Fee` },
    { key: 'signupFeeType', label: $localize`Signup Fee Type` },
    { key: 'signupFee', label: $localize`Signup Fee` },
    { key: 'serviceType', label: $localize`Service Type` },
    { key: 'apiUrl', label: $localize`API URL` },
    { key: 'authToken', label: $localize`Token` },
  ];

  filteredList: IStreamingPlatform[] = [];
  searchQuery: string = '';
  itemSelected!: IStreamingPlatform | null;
  modalTitle: string = $localize`New Streaming Platform`;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._streamingPlatformService.getAllStreamingPlatforms().subscribe({
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

  onEditItem(streamingPlatform: IStreamingPlatform): void {
    if (this.isEditable(streamingPlatform)) {
      this.modal.open();
      this.itemSelected = streamingPlatform;
    }
  }

  onDeleteItem(streamingPlatform: IStreamingPlatform): void {
    this._streamingPlatformService
      .deleteStreamingPlatformById(streamingPlatform.platformId)
      .subscribe({
        complete: () => {
          this.getList();
        },
      });
  }

  onModalClose() {
    this.itemSelected = null;
  }

  isEditable(streamingPlatform: IStreamingPlatform): boolean {
    return streamingPlatform.serviceType !== ServiceType.ACCOUNT;
  }

  refreshList(): void {
    this.modal.close();
    this.getList();
  }

  filterStreamingPlatforms(filter: string): void {
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
