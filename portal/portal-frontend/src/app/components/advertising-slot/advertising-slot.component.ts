import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-advertising-slot',
  standalone: true,
  imports: [],
  templateUrl: './advertising-slot.component.html',
  styleUrl: './advertising-slot.component.scss'
})
export class AdvertisingSlotComponent {
  @Input() width: string = '';
  @Input() height: string = '';
  @Input() image: string = '../../../assets/image-solid.svg';
  @Input() url: string = 'https://enlace_por_defecto.com';
  @Input() text: string = 'Anuncio';
}
