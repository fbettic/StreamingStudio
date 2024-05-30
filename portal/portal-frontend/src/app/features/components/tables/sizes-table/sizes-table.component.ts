import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { Header } from '../../../../core/models/header.model';
import { ISizeType } from '../../../../core/models/size-type.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { SizeTypeService } from '../../../../shared/services/size-type.service';
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
    { key: 'sizeType', label: $localize`Size Name` },
    { key: 'sizeValue', label: $localize`Priority` },
    { key: 'size', label: $localize`HeightxWidth` },
  ];

  filteredList: ISizeType[] = [];
  searchQuery: string = '';
  itemSelected!: ISizeType | null;
  modalTitle: string = $localize`New Size`;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._sizeService.getAllSizeTypes().subscribe({
      next: (res) => {
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
    });
  }

  onEditItem(size: ISizeType): void {
    this.modal.open();
    this.itemSelected = size;
  }

  onDeleteItem(size: ISizeType): void {
    this._sizeService.deleteSizeTypeById(size.sizeId!).subscribe({
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
