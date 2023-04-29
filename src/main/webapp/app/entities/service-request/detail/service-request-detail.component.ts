import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRequest } from '../service-request.model';

@Component({
  selector: 'es-service-request-detail',
  templateUrl: './service-request-detail.component.html',
})
export class ServiceRequestDetailComponent implements OnInit {
  serviceRequest: IServiceRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceRequest }) => {
      this.serviceRequest = serviceRequest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
