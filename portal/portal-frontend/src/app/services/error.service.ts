import { Injectable, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ErrorModalComponent } from '../components/shared/error-modal/error-modal.component';
import { IMessage } from '../models/message.model';

@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  private _modal: NgbModal = inject(NgbModal);

  showErrorModal(message: IMessage): void {
    const modalRef = this._modal.open(ErrorModalComponent);
    modalRef.componentInstance.message = message;
  }
}
