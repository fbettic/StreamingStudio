import { Component, inject } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { SubscriberService } from '../../../../shared/services/subscriber.service';

@Component({
  selector: 'app-association-processing',
  standalone: true,
  imports: [],
  templateUrl: './association-processing.component.html',
  styleUrl: './association-processing.component.scss',
})
export default class AssociationProcessingComponent {
  private _activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private _router: Router = inject(Router);
  private _subscriberServices: SubscriberService = inject(SubscriberService);

  uuid = '';
  constructor() {
    this._activatedRoute.queryParamMap.subscribe((param: ParamMap) => {
      this.uuid = param.get('uuid')!;
      this.finalizeAssociation();
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
