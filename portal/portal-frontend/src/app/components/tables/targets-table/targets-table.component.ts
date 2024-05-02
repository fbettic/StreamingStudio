import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { Header } from '../../../models/header.model';
import { ITargetCategory } from '../../../models/target-category.model';
import { TargetCategoryService } from '../../../services/target-category.service';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';
import { TableComponent } from '../../shared/table/table.component';
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
    { key: 'targetTitle', label: 'Target Title' },
  ];

  filteredList: ITargetCategory[] = [];
  searchQuery: string = '';
  itemSelected!: ITargetCategory | null;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._targetService.getAllTargetCategories().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._targetService.getTargetCategorys ~ res:',
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
          '🚀 ~ TableComponent ~ this._targetService.getTargetCategorys ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this._targetService.getTargetCategorys ~ complete'
        );
      },
    });
  }

  onEditItem(target: ITargetCategory): void {
    this.modal.open();
    this.itemSelected = target;
  }

  onDeleteItem(target: ITargetCategory): void {
    this._targetService.deleteTargetCategory(target.targetId!).subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._targetService.deleteTargetCategory ~ res:',
          res
        );
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._targetService.deleteTargetCategory ~ error:',
          error
        );
      },
      complete: () => {
        this.getList();
      },
    });
  }

  onModalClose() {
    console.log('🚀 ~ TargetCategorysTableComponent ~ onModalClose ~ onModalClose:');

    this.itemSelected = null;
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
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
