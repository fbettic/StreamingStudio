import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { IBannerPriority } from '../../../models/banner-priority.model';
import { Header } from '../../../models/header.model';
import { BannerPriorityService } from '../../../services/banner-priority.service';
import { BannerPriorityFormComponent } from '../../forms/banner-priority-form/banner-priority-form.component';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';
import { TableComponent } from '../../shared/table/table.component';

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
    { key: 'priorityType', label: 'Priority Type' },
    { key: 'priorityValue', label: 'Priority Value' },
  ];

  filteredList: IBannerPriority[] = [];
  searchQuery: string = '';
  itemSelected!: IBannerPriority | null;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._priorityService.getAllBannerPriorities().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._priorityService.getBannerPrioritys ~ res:',
          res
        );
        const _list = res.map((item) => {
          {
            return { ...item, canEdit: true };
          }
        });
        this._list = _list;
        this.filteredList = _list;
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._priorityService.getBannerPrioritys ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this._priorityService.getBannerPrioritys ~ complete'
        );
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
        next: (res) => {
          console.log(
            '🚀 ~ TableComponent ~ this._priorityService.deleteBannerPriority ~ res:',
            res
          );
        },
        error: (error) => {
          console.log(
            '🚀 ~ TableComponent ~ this._priorityService.deleteBannerPriority ~ error:',
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
      '🚀 ~ BannerPrioritysTableComponent ~ onModalClose ~ onModalClose:'
    );

    this.itemSelected = null;
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
    this.modal.close();
    this.getList();
  }

  filterBannerPrioritys(filter: string): void {
    console.log(
      '🚀 ~ TableComponent ~ filterBannerPrioritys ~ filter:',
      filter
    );

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
