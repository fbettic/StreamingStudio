import { Component } from '@angular/core';
import { SubscriberFormComponent } from '../../../components/forms/subscriber-form/subscriber-form.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [SubscriberFormComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

}
