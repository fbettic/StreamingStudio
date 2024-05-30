import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { IBannerPriority } from '../../../../core/models/banner-priority.model';
import { Header } from '../../../../core/models/header.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { BannerPriorityService } from '../../../../shared/services/banner-priority.service';
import { BannerPriorityFormComponent } from '../../forms/banner-priority-form/banner-priority-form.component';

@Component({
  selector: 'app-priorities-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    BannerPriorityFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './priorities-table.component.html',
  styleUrl: './priorities-table.component.scss',
})
export class PrioritiesTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private _priorityService: BannerPriorityService = inject(
    BannerPriorityService
  );
  private _list: IBannerPriority[] = [];
  headers: Header[] = [
    { key: 'priorityId', label: 'ID' },
    { key: 'priorityType', label: $localize`Priority Type` },
    { key: 'priorityValue', label: $localize`Priority Value` },
  ];

  filteredList: IBannerPriority[] = [];
  searchQuery: string = '';
  itemSelected!: IBannerPriority | null;
  modalTitle: string = $localize`New Priority`;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._priorityService.getAllBannerPriorities().subscribe({
      next: (res) => {
        const _list = res.map((item) => {
          {
            return { ...item, canEdit: true };
          }
        });
        this._list = _list;
        this.filteredList = _list;
      },
    });
  }

  onEditItem(priority: IBannerPriority): void {
    this.modal.open();
    this.itemSelected = priority;
  }

  onDeleteItem(priority: IBannerPriority): void {
    this._priorityService
      .deleteBannerPriorityById(priority.priorityId!)
      .subscribe({
        complete: () => {
          this.getList();
        },
      });
  }

  onModalClose() {

    this.itemSelected = null;
  }

  refreshList(): void {

    this.modal.close();
    this.getList();
  }

  filterBannerPrioritys(filter: string): void {


    filter = filter.toLowerCase();
    if (filter == '') {
      this.filteredList = this._list;
      return;
    }

    this.filteredList = this._list.filter((priority) => {
      return priority.priorityType.toLowerCase().includes(filter);
    });
  }
}
