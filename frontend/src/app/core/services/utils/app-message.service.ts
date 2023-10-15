import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MessageDialogComponent } from '../../layouts/message-dialog/message-dialog.component';
import { IMessage } from '../../models/i-message';

@Injectable({
  providedIn: 'root',
})
export class AppMessageService {
  constructor(private _modal: MatDialog) {}

  public showMessage(message: IMessage): void {
    const modalRef = this._modal.open(MessageDialogComponent);
    modalRef.componentInstance.message = message;
    modalRef.afterClosed().subscribe((res) => {
      console.log(res);
    });
  }
}
