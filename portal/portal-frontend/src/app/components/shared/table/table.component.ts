import { CommonModule } from '@angular/common';
import { Component,  EventEmitter,  Input, Output } from '@angular/core';
import { Header } from '../../../models/header.model';

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
  @Output() editClicked: EventEmitter<any> = new EventEmitter<any>();
  @Output() deleteClicked: EventEmitter<any> = new EventEmitter<any>();
  @Output() isItemEditable: EventEmitter<any> = new EventEmitter<any>();

  onEditClick(item: any) {
    this.editClicked.emit(item);
  }

  onDeleteClick(item: any) {
    this.deleteClicked.emit(item);
  }

  isEditable(item : any): boolean {
    return item.canEdit!==false;
  }

  getKeys(): string[] {
    return Object.keys(this.items);
  }
}
