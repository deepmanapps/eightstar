import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParkingAll } from '../parking-all.model';

@Component({
  selector: 'es-parking-all-detail',
  templateUrl: './parking-all-detail.component.html',
})
export class ParkingAllDetailComponent implements OnInit {
  parkingAll: IParkingAll | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parkingAll }) => {
      this.parkingAll = parkingAll;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
