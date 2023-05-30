import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DeliveryRequestPlaceFormService, DeliveryRequestPlaceFormGroup } from './delivery-request-place-form.service';
import { IDeliveryRequestPlace } from '../delivery-request-place.model';
import { DeliveryRequestPlaceService } from '../service/delivery-request-place.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';

@Component({
  selector: 'es-delivery-request-place-update',
  templateUrl: './delivery-request-place-update.component.html',
})
export class DeliveryRequestPlaceUpdateComponent implements OnInit {
  isSaving = false;
  deliveryRequestPlace: IDeliveryRequestPlace | null = null;

  hotelsSharedCollection: IHotel[] = [];

  editForm: DeliveryRequestPlaceFormGroup = this.deliveryRequestPlaceFormService.createDeliveryRequestPlaceFormGroup();

  constructor(
    protected deliveryRequestPlaceService: DeliveryRequestPlaceService,
    protected deliveryRequestPlaceFormService: DeliveryRequestPlaceFormService,
    protected hotelService: HotelService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliveryRequestPlace }) => {
      this.deliveryRequestPlace = deliveryRequestPlace;
      if (deliveryRequestPlace) {
        this.updateForm(deliveryRequestPlace);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deliveryRequestPlace = this.deliveryRequestPlaceFormService.getDeliveryRequestPlace(this.editForm);
    if (deliveryRequestPlace.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.deliveryRequestPlaceService.update(deliveryRequestPlace));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.deliveryRequestPlaceService.create(deliveryRequestPlace));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryRequestPlace>>): void {
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

  protected updateForm(deliveryRequestPlace: IDeliveryRequestPlace): void {
    this.deliveryRequestPlace = deliveryRequestPlace;
    this.deliveryRequestPlaceFormService.resetForm(this.editForm, deliveryRequestPlace);

    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(
      this.hotelsSharedCollection,
      deliveryRequestPlace.hotel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.deliveryRequestPlace?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));
  }
}
