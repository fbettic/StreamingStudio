import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMessage } from '../../models/message.model';

@Component({
  selector: 'app-error-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './error-modal.component.html',
  styleUrl: './error-modal.component.scss',
})
export class ErrorModalComponent {
  private _modalService = inject(NgbModal);

  message?: IMessage;

  constructor() {}

  close() {
    this._modalService.dismissAll();
  }
}
