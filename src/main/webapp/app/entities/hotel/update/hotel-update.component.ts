import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HotelFormService, HotelFormGroup } from './hotel-form.service';
import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';

@Component({
  selector: 'es-hotel-update',
  templateUrl: './hotel-update.component.html',
})
export class HotelUpdateComponent implements OnInit {
  isSaving = false;
  hotel: IHotel | null = null;

  locationsCollection: ILocation[] = [];

  editForm: HotelFormGroup = this.hotelFormService.createHotelFormGroup();

  constructor(
    protected hotelService: HotelService,
    protected hotelFormService: HotelFormService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLocation = (o1: ILocation | null, o2: ILocation | null): boolean => this.locationService.compareLocation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotel }) => {
      this.hotel = hotel;
      if (hotel) {
        this.updateForm(hotel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotel = this.hotelFormService.getHotel(this.editForm);
    if (hotel.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.hotelService.update(hotel));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.hotelService.create(hotel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotel>>): void {
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

  protected updateForm(hotel: IHotel): void {
    this.hotel = hotel;
    this.hotelFormService.resetForm(this.editForm, hotel);

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing<ILocation>(this.locationsCollection, hotel.location);
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'hotel-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) => this.locationService.addLocationToCollectionIfMissing<ILocation>(locations, this.hotel?.location))
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));
  }
}
