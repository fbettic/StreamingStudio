import {
  Component,
  Input,
  TemplateRef,
  ViewChild,
  inject,
} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-form',
  standalone: true,
  imports: [],
  templateUrl: './modal-form.component.html',
  styleUrl: './modal-form.component.scss',
})
export class ModalFormComponent {
  @ViewChild('content') content!: TemplateRef<any>;

  private modalService = inject(NgbModal);

  modalReference?: NgbModalRef;

  @Input() modalTitle: string = '';

  open(): void {
    this.modalReference = this.modalService.open(this.content, {
      ariaLabelledBy: 'form-modal',
      size: 'lg',
    });
  }

  close(){
	this.modalReference?.close()
  }
}
