import { Component, inject } from '@angular/core';
import { ActivatedRoute, ParamMap, Route, Router } from '@angular/router';
import { SubscriberService } from '../../../services/subscriber.service';
import { Role } from '../../../enums/role.enum';

@Component({
  selector: 'app-association-processing',
  standalone: true,
  imports: [],
  templateUrl: './association-processing.component.html',
  styleUrl: './association-processing.component.scss',
})
export class AssociationProcessingComponent {
  private _activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private _router: Router = inject(Router);
  private _subscriberServices: SubscriberService = inject(SubscriberService);

  uuid = '';
  constructor() {
    this._activatedRoute.queryParamMap.subscribe((param: ParamMap) => {
      this.uuid = param.get('uuid')!;
      this.finalizeAssociation()
    });
  }

  finalizeAssociation() {
    this._subscriberServices.getCompleteAssociation(this.uuid).subscribe({
      next: () => {
        this._router.navigateByUrl('/home');
      },
    });
  }
}
