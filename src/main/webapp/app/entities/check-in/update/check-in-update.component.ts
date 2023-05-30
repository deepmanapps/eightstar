import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CheckInFormService, CheckInFormGroup } from './check-in-form.service';
import { ICheckIn } from '../check-in.model';
import { CheckInService } from '../service/check-in.service';
import { ICheckOut } from 'app/entities/check-out/check-out.model';
import { CheckOutService } from 'app/entities/check-out/service/check-out.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { Status } from 'app/entities/enumerations/status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

@Component({
  selector: 'es-check-in-update',
  templateUrl: './check-in-update.component.html',
})
export class CheckInUpdateComponent implements OnInit {
  isSaving = false;
  checkIn: ICheckIn | null = null;
  statusValues = Object.keys(Status);
  paymentMethodValues = Object.keys(PaymentMethod);

  checkOutsCollection: ICheckOut[] = [];
  hotelsSharedCollection: IHotel[] = [];
  customersSharedCollection: ICustomer[] = [];

  editForm: CheckInFormGroup = this.checkInFormService.createCheckInFormGroup();

  constructor(
    protected checkInService: CheckInService,
    protected checkInFormService: CheckInFormService,
    protected checkOutService: CheckOutService,
    protected hotelService: HotelService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCheckOut = (o1: ICheckOut | null, o2: ICheckOut | null): boolean => this.checkOutService.compareCheckOut(o1, o2);

  compareHotel = (o1: IHotel | null, o2: IHotel | null): boolean => this.hotelService.compareHotel(o1, o2);

  compareCustomer = (o1: ICustomer | null, o2: ICustomer | null): boolean => this.customerService.compareCustomer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkIn }) => {
      this.checkIn = checkIn;
      if (checkIn) {
        this.updateForm(checkIn);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkIn = this.checkInFormService.getCheckIn(this.editForm);
    if (checkIn.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.checkInService.update(checkIn));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.checkInService.create(checkIn));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckIn>>): void {
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

  protected updateForm(checkIn: ICheckIn): void {
    this.checkIn = checkIn;
    this.checkInFormService.resetForm(this.editForm, checkIn);

    this.checkOutsCollection = this.checkOutService.addCheckOutToCollectionIfMissing<ICheckOut>(this.checkOutsCollection, checkIn.checkOut);
    this.hotelsSharedCollection = this.hotelService.addHotelToCollectionIfMissing<IHotel>(this.hotelsSharedCollection, checkIn.hotel);
    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing<ICustomer>(
      this.customersSharedCollection,
      checkIn.customer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.checkOutService
      .query({ filter: 'checkin-is-null' })
      .pipe(map((res: HttpResponse<ICheckOut[]>) => res.body ?? []))
      .pipe(
        map((checkOuts: ICheckOut[]) => this.checkOutService.addCheckOutToCollectionIfMissing<ICheckOut>(checkOuts, this.checkIn?.checkOut))
      )
      .subscribe((checkOuts: ICheckOut[]) => (this.checkOutsCollection = checkOuts));

    this.hotelService
      .query()
      .pipe(map((res: HttpResponse<IHotel[]>) => res.body ?? []))
      .pipe(map((hotels: IHotel[]) => this.hotelService.addHotelToCollectionIfMissing<IHotel>(hotels, this.checkIn?.hotel)))
      .subscribe((hotels: IHotel[]) => (this.hotelsSharedCollection = hotels));

    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) => this.customerService.addCustomerToCollectionIfMissing<ICustomer>(customers, this.checkIn?.customer))
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));
  }
}
