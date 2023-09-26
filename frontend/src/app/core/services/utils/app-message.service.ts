import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageDialogComponent } from '../../layouts/message-dialog/message-dialog.component';
import { IMessage } from '../../models/i-message';

@Injectable({
  providedIn: 'root'
})
export class AppMessageService {

  constructor(private _modal: NgbModal) { }

  public showMessage(message: IMessage): void {
    const modalRef = this._modal.open(MessageDialogComponent);
          modalRef.componentInstance.message = message;          
  }
  
}
