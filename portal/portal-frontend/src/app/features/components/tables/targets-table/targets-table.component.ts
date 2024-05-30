import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { Header } from '../../../../core/models/header.model';
import { ITargetCategory } from '../../../../core/models/target-category.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { TargetCategoryService } from '../../../../shared/services/target-category.service';
import { TargetFormComponent } from '../../forms/target-form/target-form.component';

@Component({
  selector: 'app-targets-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    TargetFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './targets-table.component.html',
  styleUrl: './targets-table.component.scss',
})
export class TargetsTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private _targetService: TargetCategoryService = inject(TargetCategoryService);
  private _list: ITargetCategory[] = [];
  headers: Header[] = [
    { key: 'targetId', label: 'ID' },
    { key: 'targetTitle', label: $localize`Target Title` },
  ];

  filteredList: ITargetCategory[] = [];
  searchQuery: string = '';
  itemSelected!: ITargetCategory | null;
  modalTitle: string = $localize`New Target`;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._targetService.getAllTargetCategories().subscribe({
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

  onEditItem(target: ITargetCategory): void {
    this.modal.open();
    this.itemSelected = target;
  }

  onDeleteItem(target: ITargetCategory): void {
    this._targetService.deleteTargetCategory(target.targetId!).subscribe({
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

  filterTargetCategory(filter: string): void {
    filter = filter.toLowerCase();
    if (filter == '') {
      this.filteredList = this._list;
      return;
    }

    this.filteredList = this._list.filter((target) => {
      return target.targetTitle.toLowerCase().includes(filter);
    });
  }
}
