import {
  Component,
  EventEmitter,
  Input,
  Output,
  TemplateRef,
  ViewChild,
  effect,
  inject,
  input,
} from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-custom-modal',
  standalone: true,
  imports: [],
  templateUrl: './custom-modal.component.html',
  styleUrl: './custom-modal.component.scss',
})
export class CustomModalComponent {
  @ViewChild('content') content!: TemplateRef<any>;


  private modalService = inject(NgbModal);

  modalReference?: NgbModalRef;

  showButton=input<boolean>(true);
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
    console.log("🚀 ~ CustomModalComponent ~ onCloseButtonClick ~ modal:", modal)
    
    modal.dismiss('Cross click')
    this.onClose.emit();
  }
 
}
