import { Component, inject } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { IAssociation } from '../../../../core/models/association.model';
import { IObvservation } from '../../../../core/models/obvservation.model';
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
  showForm: boolean = false;
  association?: IAssociation;
  obvservation: string = '';

  constructor() {
    this._activatedRoute.queryParamMap.subscribe((param: ParamMap) => {
      this.uuid = param.get('uuid')!;
      this.finalizeAssociation();
    });

    
  }

  finalizeAssociation() {
    this._subscriberServices.getCompleteAssociation(this.uuid).subscribe({
      next: (associationData) => {
        console.log(
          '🚀 ~ AssociationProcessingComponent ~ this._subscriberServices.getCompleteAssociation ~ associationData:',
          associationData
        );

        this.showForm = associationData.userToken ? true : false;
        this.association = associationData;
      },
      error: (error) => {
        this.showForm = false;
        this._router.navigateByUrl('/home')
      },
    });
  }

  onObvservationChange(text: string) {
    console.log(
      '🚀 ~ AssociationProcessingComponent ~ onObvservationChange ~ text:',
      text
    );
    this.obvservation = text;
  }

  sendObvservation(e:any) {
    e.preventDefault();
    const data: IObvservation = {
      transactionId: this.association?.transactionId!,
      subscriberId: this.association?.subscriberId!,
      platformId: this.association?.platformId!,
      obvservation: this.obvservation,
    };

    this._subscriberServices.updateObvservation(data).subscribe({
      next: (data) => {
        console.log(
          '🚀 ~ AssociationProcessingComponent ~ sendObvservation ~ data:',
          data
        );
        this._router.navigateByUrl('/home')
      },
      error: (error) => {
        alert(error.error);
      },
    });
  }

  skipObvservation() {
    this._router.navigateByUrl('/home')
  }
}
