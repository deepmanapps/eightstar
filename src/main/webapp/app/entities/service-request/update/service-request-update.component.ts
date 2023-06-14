import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ServiceRequestFormService, ServiceRequestFormGroup } from './service-request-form.service';
import { IServiceRequest } from '../service-request.model';
import { ServiceRequestService } from '../service/service-request.service';
import { IParkingAll } from 'app/entities/parking-all/parking-all.model';
import { ParkingAllService } from 'app/entities/parking-all/service/parking-all.service';
import { IDeliveryRequestPlace } from 'app/entities/delivery-request-place/delivery-request-place.model';
import { DeliveryRequestPlaceService } from 'app/entities/delivery-request-place/service/delivery-request-place.service';
import { IServices } from 'app/entities/services/services.model';
import { ServicesService } from 'app/entities/services/service/services.service';
import { ICheckIn } from 'app/entities/check-in/check-in.model';
import { CheckInService } from 'app/entities/check-in/service/check-in.service';
import { RQStatus } from 'app/entities/enumerations/rq-status.model';

@Component({
  selector: 'es-service-request-update',
  templateUrl: './service-request-update.component.html',
})
export class ServiceRequestUpdateComponent implements OnInit {
  isSaving = false;
  serviceRequest: IServiceRequest | null = null;
  rQStatusValues = Object.keys(RQStatus);

  parkingAllsCollection: IParkingAll[] = [];
  deliveryRequestPlacesCollection: IDeliveryRequestPlace[] = [];
  servicesSharedCollection: IServices[] = [];
  checkInsSharedCollection: ICheckIn[] = [];

  editForm: ServiceRequestFormGroup = this.serviceRequestFormService.createServiceRequestFormGroup();

  constructor(
    protected serviceRequestService: ServiceRequestService,
    protected serviceRequestFormService: ServiceRequestFormService,
    protected parkingAllService: ParkingAllService,
    protected deliveryRequestPlaceService: DeliveryRequestPlaceService,
    protected servicesService: ServicesService,
    protected checkInService: CheckInService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareParkingAll = (o1: IParkingAll | null, o2: IParkingAll | null): boolean => this.parkingAllService.compareParkingAll(o1, o2);

  compareDeliveryRequestPlace = (o1: IDeliveryRequestPlace | null, o2: IDeliveryRequestPlace | null): boolean =>
    this.deliveryRequestPlaceService.compareDeliveryRequestPlace(o1, o2);

  compareServices = (o1: IServices | null, o2: IServices | null): boolean => this.servicesService.compareServices(o1, o2);

  compareCheckIn = (o1: ICheckIn | null, o2: ICheckIn | null): boolean => this.checkInService.compareCheckIn(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceRequest }) => {
      this.serviceRequest = serviceRequest;
      if (serviceRequest) {
        this.updateForm(serviceRequest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceRequest = this.serviceRequestFormService.getServiceRequest(this.editForm);
    if (serviceRequest.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.serviceRequestService.update(serviceRequest));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.serviceRequestService.create(serviceRequest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRequest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(serviceRequest: IServiceRequest): void {
    this.serviceRequest = serviceRequest;
    this.serviceRequestFormService.resetForm(this.editForm, serviceRequest);

    this.parkingAllsCollection = this.parkingAllService.addParkingAllToCollectionIfMissing<IParkingAll>(
      this.parkingAllsCollection,
      serviceRequest.parkingAll
    );
    this.deliveryRequestPlacesCollection =
      this.deliveryRequestPlaceService.addDeliveryRequestPlaceToCollectionIfMissing<IDeliveryRequestPlace>(
        this.deliveryRequestPlacesCollection,
        serviceRequest.deliveryRequestPlace
      );
    this.servicesSharedCollection = this.servicesService.addServicesToCollectionIfMissing<IServices>(
      this.servicesSharedCollection,
      serviceRequest.services
    );
    this.checkInsSharedCollection = this.checkInService.addCheckInToCollectionIfMissing<ICheckIn>(
      this.checkInsSharedCollection,
      serviceRequest.checkIn
    );
  }

  protected loadRelationshipsOptions(): void {
    this.parkingAllService
      .query({ filter: 'servicerequest-is-null' })
      .pipe(map((res: HttpResponse<IParkingAll[]>) => res.body ?? []))
      .pipe(
        map((parkingAlls: IParkingAll[]) =>
          this.parkingAllService.addParkingAllToCollectionIfMissing<IParkingAll>(parkingAlls, this.serviceRequest?.parkingAll)
        )
      )
      .subscribe((parkingAlls: IParkingAll[]) => (this.parkingAllsCollection = parkingAlls));

    this.deliveryRequestPlaceService
      .query({ filter: 'servicerequest-is-null' })
      .pipe(map((res: HttpResponse<IDeliveryRequestPlace[]>) => res.body ?? []))
      .pipe(
        map((deliveryRequestPlaces: IDeliveryRequestPlace[]) =>
          this.deliveryRequestPlaceService.addDeliveryRequestPlaceToCollectionIfMissing<IDeliveryRequestPlace>(
            deliveryRequestPlaces,
            this.serviceRequest?.deliveryRequestPlace
          )
        )
      )
      .subscribe((deliveryRequestPlaces: IDeliveryRequestPlace[]) => (this.deliveryRequestPlacesCollection = deliveryRequestPlaces));

    this.servicesService
      .query()
      .pipe(map((res: HttpResponse<IServices[]>) => res.body ?? []))
      .pipe(
        map((services: IServices[]) =>
          this.servicesService.addServicesToCollectionIfMissing<IServices>(services, this.serviceRequest?.services)
        )
      )
      .subscribe((services: IServices[]) => (this.servicesSharedCollection = services));

    this.checkInService
      .query()
      .pipe(map((res: HttpResponse<ICheckIn[]>) => res.body ?? []))
      .pipe(
        map((checkIns: ICheckIn[]) => this.checkInService.addCheckInToCollectionIfMissing<ICheckIn>(checkIns, this.serviceRequest?.checkIn))
      )
      .subscribe((checkIns: ICheckIn[]) => (this.checkInsSharedCollection = checkIns));
  }
}
