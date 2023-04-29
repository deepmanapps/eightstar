import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckIn } from '../check-in.model';

@Component({
  selector: 'es-check-in-detail',
  templateUrl: './check-in-detail.component.html',
})
export class CheckInDetailComponent implements OnInit {
  checkIn: ICheckIn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkIn }) => {
      this.checkIn = checkIn;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
