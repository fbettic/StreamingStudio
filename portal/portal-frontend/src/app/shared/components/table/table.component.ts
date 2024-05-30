import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Header } from '../../../core/models/header.model';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss',
})
export class TableComponent {
  @Input() headers: Header[] = [];
  @Input() items: any[] = [];
  @Input() extraButton: String = '';

  @Output() onEdit: EventEmitter<any> = new EventEmitter<any>();
  @Output() onFilterInput: EventEmitter<string> = new EventEmitter<string>();
  @Output() onDelete: EventEmitter<any> = new EventEmitter<any>();
  @Output() onExtraClick: EventEmitter<any> = new EventEmitter<any>();

  editClicked(item: any) {
    this.onEdit.emit(item);
  }

  deleteClicked(item: any) {
    this.onEdit.emit(item);
  }

  filterInput(query: string) {
    this.onFilterInput.emit(query);
  }

  extraButtonClicked(item:any){
    this.onExtraClick.emit(item)
  }

  isEditable(item: any): boolean {
    return item.canEdit !== false;
  }

  getKeys(): string[] {
    return Object.keys(this.items);
  }
}
