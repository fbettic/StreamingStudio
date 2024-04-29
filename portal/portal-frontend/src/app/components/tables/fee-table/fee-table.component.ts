import { Component, ViewChild, inject } from '@angular/core';
import { FeeService } from '../../../services/fee.service';
import { ServiceType } from '../../../enums/service-type.enum';
import { IFee } from '../../../models/fee.model';
import { Header } from '../../../models/header.model';
import { CustomModalComponent } from '../../shared/custom-modal/custom-modal.component';
import { CommonModule } from '@angular/common';
import { FeeFormComponent } from '../../forms/fee-form/fee-form.component';
import { TableComponent } from '../../shared/table/table.component';

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
    { key: 'feeType', label: 'Fee Type' },
    { key: 'feeValue', label: 'Fee Value' },
  ];

  filteredList: IFee[] = [];
  searchQuery: string = '';
  itemSelected!: IFee | null;

  constructor() {
    this.getList();
  }

  getList(): void {
    this._feeService.getAllFees().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._feeService.getFees ~ res:',
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
          '🚀 ~ TableComponent ~ this._feeService.getFees ~ error:',
          error
        );
      },
      complete: () => {
        console.log(
          '🚀 ~ TableComponent ~ this._feeService.getFees ~ complete'
        );
      },
    });
  }

  onEditItem(fee: IFee): void {
    this.modal.open();
    this.itemSelected = fee;
  }

  onDeleteItem(fee: IFee): void {
    this._feeService.deleteFeeById(fee.feeId!).subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ TableComponent ~ this._feeService.deleteFee ~ res:',
          res
        );
      },
      error: (error) => {
        console.log(
          '🚀 ~ TableComponent ~ this._feeService.deleteFee ~ error:',
          error
        );
      },
      complete: () => {
        this.getList();
      },
    });
  }

  onModalClose() {
    console.log('🚀 ~ FeesTableComponent ~ onModalClose ~ onModalClose:');

    this.itemSelected = null;
  }

  refreshList(): void {
    console.log('🚀 ~ TableComponent ~ refreshList ~ refreshList:');
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
