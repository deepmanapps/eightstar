import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CheckInFormService } from './check-in-form.service';
import { CheckInService } from '../service/check-in.service';
import { ICheckIn } from '../check-in.model';
import { ICheckOut } from 'app/entities/check-out/check-out.model';
import { CheckOutService } from 'app/entities/check-out/service/check-out.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';

import { CheckInUpdateComponent } from './check-in-update.component';

describe('CheckIn Management Update Component', () => {
  let comp: CheckInUpdateComponent;
  let fixture: ComponentFixture<CheckInUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let checkInFormService: CheckInFormService;
  let checkInService: CheckInService;
  let checkOutService: CheckOutService;
  let hotelService: HotelService;
  let customerService: CustomerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CheckInUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CheckInUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckInUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    checkInFormService = TestBed.inject(CheckInFormService);
    checkInService = TestBed.inject(CheckInService);
    checkOutService = TestBed.inject(CheckOutService);
    hotelService = TestBed.inject(HotelService);
    customerService = TestBed.inject(CustomerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call checkOut query and add missing value', () => {
      const checkIn: ICheckIn = { id: 456 };
      const checkOut: ICheckOut = { id: 38800 };
      checkIn.checkOut = checkOut;

      const checkOutCollection: ICheckOut[] = [{ id: 40071 }];
      jest.spyOn(checkOutService, 'query').mockReturnValue(of(new HttpResponse({ body: checkOutCollection })));
      const expectedCollection: ICheckOut[] = [checkOut, ...checkOutCollection];
      jest.spyOn(checkOutService, 'addCheckOutToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkIn });
      comp.ngOnInit();

      expect(checkOutService.query).toHaveBeenCalled();
      expect(checkOutService.addCheckOutToCollectionIfMissing).toHaveBeenCalledWith(checkOutCollection, checkOut);
      expect(comp.checkOutsCollection).toEqual(expectedCollection);
    });

    it('Should call Hotel query and add missing value', () => {
      const checkIn: ICheckIn = { id: 456 };
      const hotel: IHotel = { id: 41829 };
      checkIn.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 51679 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkIn });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining)
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Customer query and add missing value', () => {
      const checkIn: ICheckIn = { id: 456 };
      const customer: ICustomer = { id: 34078 };
      checkIn.customer = customer;

      const customerCollection: ICustomer[] = [{ id: 83266 }];
      jest.spyOn(customerService, 'query').mockReturnValue(of(new HttpResponse({ body: customerCollection })));
      const additionalCustomers = [customer];
      const expectedCollection: ICustomer[] = [...additionalCustomers, ...customerCollection];
      jest.spyOn(customerService, 'addCustomerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ checkIn });
      comp.ngOnInit();

      expect(customerService.query).toHaveBeenCalled();
      expect(customerService.addCustomerToCollectionIfMissing).toHaveBeenCalledWith(
        customerCollection,
        ...additionalCustomers.map(expect.objectContaining)
      );
      expect(comp.customersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const checkIn: ICheckIn = { id: 456 };
      const checkOut: ICheckOut = { id: 41319 };
      checkIn.checkOut = checkOut;
      const hotel: IHotel = { id: 94165 };
      checkIn.hotel = hotel;
      const customer: ICustomer = { id: 24287 };
      checkIn.customer = customer;

      activatedRoute.data = of({ checkIn });
      comp.ngOnInit();

      expect(comp.checkOutsCollection).toContain(checkOut);
      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.customersSharedCollection).toContain(customer);
      expect(comp.checkIn).toEqual(checkIn);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckIn>>();
      const checkIn = { id: 123 };
      jest.spyOn(checkInFormService, 'getCheckIn').mockReturnValue(checkIn);
      jest.spyOn(checkInService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkIn });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkIn }));
      saveSubject.complete();

      // THEN
      expect(checkInFormService.getCheckIn).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(checkInService.update).toHaveBeenCalledWith(expect.objectContaining(checkIn));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckIn>>();
      const checkIn = { id: 123 };
      jest.spyOn(checkInFormService, 'getCheckIn').mockReturnValue({ id: null });
      jest.spyOn(checkInService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkIn: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkIn }));
      saveSubject.complete();

      // THEN
      expect(checkInFormService.getCheckIn).toHaveBeenCalled();
      expect(checkInService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckIn>>();
      const checkIn = { id: 123 };
      jest.spyOn(checkInService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkIn });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(checkInService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCheckOut', () => {
      it('Should forward to checkOutService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(checkOutService, 'compareCheckOut');
        comp.compareCheckOut(entity, entity2);
        expect(checkOutService.compareCheckOut).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareHotel', () => {
      it('Should forward to hotelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelService, 'compareHotel');
        comp.compareHotel(entity, entity2);
        expect(hotelService.compareHotel).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCustomer', () => {
      it('Should forward to customerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(customerService, 'compareCustomer');
        comp.compareCustomer(entity, entity2);
        expect(customerService.compareCustomer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
