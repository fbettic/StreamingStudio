import { CommonModule } from '@angular/common';
import { Component, ViewChild, inject } from '@angular/core';
import { IFee } from '../../../../core/models/fee.model';
import { Header } from '../../../../core/models/header.model';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { TableComponent } from '../../../../shared/components/table/table.component';
import { FeeService } from '../../../../shared/services/fee.service';
import { FeeFormComponent } from '../../forms/fee-form/fee-form.component';

@Component({
  selector: 'app-fee-table',
  standalone: true,
  imports: [
    CommonModule,
    TableComponent,
    FeeFormComponent,
    CustomModalComponent,
  ],
  templateUrl: './fee-table.component.html',
  styleUrl: './fee-table.component.scss',
})
export class FeeTableComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;

  private _feeService: FeeService = inject(FeeService);
  private _list: IFee[] = [];
  headers: Header[] = [
    { key: 'feeId', label: 'ID' },
    { key: 'feeType', label: $localize`Fee Type` },
    { key: 'feeValue', label: $localize`Fee Value` },
  ];

  filteredList: IFee[] = [];
  searchQuery: string = '';
  itemSelected!: IFee | null;
  modalTitle: string = $localize`New Fee`;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._feeService.getAllFees().subscribe({
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

  onEditItem(fee: IFee): void {
    this.modal.open();
    this.itemSelected = fee;
  }

  onDeleteItem(fee: IFee): void {
    this._feeService.deleteFeeById(fee.feeId!).subscribe({
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

  filterFee(filter: string): void {
    filter = filter.toLowerCase();
    if (filter == '') {
      this.filteredList = this._list;
      return;
    }

    this.filteredList = this._list.filter((fee) => {
      return fee.feeType.toLowerCase().includes(filter);
    });
  }
}
