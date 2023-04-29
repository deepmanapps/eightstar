import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HotelServicesFormService, HotelServicesFormGroup } from './hotel-services-form.service';
import { IHotelServices } from '../hotel-services.model';
import { HotelServicesService } from '../service/hotel-services.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { IServices } from 'app/entities/services/services.model';
import { ServicesService } from 'app/entities/services/service/services.service';

@Component({
  selector: 'es-hotel-services-update',
  templateUrl: './hotel-services-update.component.html',
})
export class HotelServicesUpdateComponent implements OnInit {
  isSaving = false;
  hotelServices: IHotelServices | null = null;

  hotelsSharedCollection: IHotel[] = [];
  servicesSharedCollection: IServices[] = [];

  editForm: HotelServicesFormGroup = this.hotelServicesFormService.createHotelServicesFormGroup();

  constructor(
    protected hotelServicesService: HotelServicesService,
    protected hotelServicesFormService: HotelServicesFormService,
    protected hotelService: HotelService,
    protected servicesService: ServicesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  compareServices = (o1: IServices | null, o2: IServices | null): boolean => this.servicesService.compareServices(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelServices }) => {
      this.hotelServices = hotelServices;
      if (hotelServices) {
        this.updateForm(hotelServices);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotelServices = this.hotelServicesFormService.getHotelServices(this.editForm);
    if (hotelServices.id !== null) {
      this.subscribeToSaveResponse(this.hotelServicesService.update(hotelServices));
    } else {
      this.subscribeToSaveResponse(this.hotelServicesService.create(hotelServices));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelServices>>): void {
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

  protected updateForm(hotelServices: IHotelServices): void {
    this.hotelServices = hotelServices;
    this.hotelServicesFormService.resetForm(this.editForm, hotelServices);

    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(this.hotelsSharedCollection, hotelServices.hotel);
    this.servicesSharedCollection = this.servicesService.addServicesToCollectionIfMissing<IServices>(
      this.servicesSharedCollection,
      hotelServices.services
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.hotelServices?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));

    this.servicesService
      .query()
      .pipe(map((res: HttpResponse<IServices[]>) => res.body ?? []))
      .pipe(
        map((services: IServices[]) =>
          this.servicesService.addServicesToCollectionIfMissing<IServices>(services, this.hotelServices?.services)
        )
      )
      .subscribe((services: IServices[]) => (this.servicesSharedCollection = services));
  }
}
