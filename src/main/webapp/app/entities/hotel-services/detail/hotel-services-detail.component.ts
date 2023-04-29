import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHotelServices } from '../hotel-services.model';

@Component({
  selector: 'es-hotel-services-detail',
  templateUrl: './hotel-services-detail.component.html',
})
export class HotelServicesDetailComponent implements OnInit {
  hotelServices: IHotelServices | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelServices }) => {
      this.hotelServices = hotelServices;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
