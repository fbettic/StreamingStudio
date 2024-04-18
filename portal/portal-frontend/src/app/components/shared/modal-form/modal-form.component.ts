import {
  Component,
  EventEmitter,
  Input,
  Output,
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
  @Output() onClose: EventEmitter<void> = new EventEmitter<void>();
  
  open(): void {
    this.modalReference = this.modalService.open(this.content, {
      ariaLabelledBy: 'form-modal',
      size: 'lg',
    });
  }

  close(){
	  this.modalReference?.close()
    this.onClose.emit();
  }

  onCloseButtonClick(modal: any){
    console.log("🚀 ~ ModalFormComponent ~ onCloseButtonClick ~ modal:", modal)
    
    modal.dismiss('Cross click')
    this.onClose.emit();
  }
 
}
