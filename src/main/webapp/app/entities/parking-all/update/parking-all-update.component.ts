import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ParkingAllFormService, ParkingAllFormGroup } from './parking-all-form.service';
import { IParkingAll } from '../parking-all.model';
import { ParkingAllService } from '../service/parking-all.service';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';

@Component({
  selector: 'es-parking-all-update',
  templateUrl: './parking-all-update.component.html',
})
export class ParkingAllUpdateComponent implements OnInit {
  isSaving = false;
  parkingAll: IParkingAll | null = null;

  locationsCollection: ILocation[] = [];
  hotelsSharedCollection: IHotel[] = [];

  editForm: ParkingAllFormGroup = this.parkingAllFormService.createParkingAllFormGroup();

  constructor(
    protected parkingAllService: ParkingAllService,
    protected parkingAllFormService: ParkingAllFormService,
    protected locationService: LocationService,
    protected hotelService: HotelService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLocation = (o1: ILocation | null, o2: ILocation | null): boolean => this.locationService.compareLocation(o1, o2);

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parkingAll }) => {
      this.parkingAll = parkingAll;
      if (parkingAll) {
        this.updateForm(parkingAll);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parkingAll = this.parkingAllFormService.getParkingAll(this.editForm);
    if (parkingAll.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.parkingAllService.update(parkingAll));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.parkingAllService.create(parkingAll));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParkingAll>>): void {
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

  protected updateForm(parkingAll: IParkingAll): void {
    this.parkingAll = parkingAll;
    this.parkingAllFormService.resetForm(this.editForm, parkingAll);

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing<ILocation>(
      this.locationsCollection,
      parkingAll.location
    );
    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(this.hotelsSharedCollection, parkingAll.hotel);
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'parkingall-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing<ILocation>(locations, this.parkingAll?.location)
        )
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));

    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.parkingAll?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));
  }
}
