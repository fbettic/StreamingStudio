import { Component, ViewChild, inject } from '@angular/core';
import { Header } from '../../../models/header.model';
import { ISizeType } from '../../../models/size-type.model';
import { SizeTypeService } from '../../../services/size-type.service';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';
import { CommonModule } from '@angular/common';
import { FeeFormComponent } from '../../forms/fee-form/fee-form.component';
import { TableComponent } from '../../shared/table/table.component';
import { SizeTypeFormComponent } from '../../forms/size-type-form/size-type-form.component';

@Component({
  selector: 'app-sizes-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    SizeTypeFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './sizes-table.component.html',
  styleUrl: './sizes-table.component.scss',
})
export class SizesTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private _sizeService: SizeTypeService = inject(SizeTypeService);
  private _list: ISizeType[] = [];
  headers: Header[] = [
    { key: 'sizeId', label: 'ID' },
    { key: 'sizeType', label: 'Size Name' },
    { key: 'sizeValue', label: 'Priority' },
    { key: 'size', label: 'HeightxWidth' },
  ];

  filteredList: ISizeType[] = [];
  searchQuery: string = '';
  itemSelected!: ISizeType | null;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._sizeService.getAllSizeTypes().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._sizeService.getSizeTypes ~ res:',
          res
        );
        const _list = res.map((item) => {
          {
            return {
              ...item,
              canEdit: true,
              size: `${item.height}x${item.width}`,
            };
          }
        });
        this._list = _list;
        this.filteredList = _list;
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._sizeService.getSizeTypes ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this._sizeService.getSizeTypes ~ complete'
        );
      },
    });
  }

  onEditItem(size: ISizeType): void {
    this.modal.open();
    this.itemSelected = size;
  }

  onDeleteItem(size: ISizeType): void {
    this._sizeService.deleteSizeTypeById(size.sizeId!).subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._sizeService.deleteSizeType ~ res:',
          res
        );
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._sizeService.deleteSizeType ~ error:',
          error
        );
      },
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

  filterSizeTypes(filter: string): void {
    filter = filter.toLowerCase();
    if (filter == '') {
      this.filteredList = this._list;
      return;
    }

    this.filteredList = this._list.filter((size) => {
      return size.sizeType.toLowerCase().includes(filter);
    });
  }
}
