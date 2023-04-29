import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeliveryRequestPlace } from '../delivery-request-place.model';

@Component({
  selector: 'es-delivery-request-place-detail',
  templateUrl: './delivery-request-place-detail.component.html',
})
export class DeliveryRequestPlaceDetailComponent implements OnInit {
  deliveryRequestPlace: IDeliveryRequestPlace | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliveryRequestPlace }) => {
      this.deliveryRequestPlace = deliveryRequestPlace;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
