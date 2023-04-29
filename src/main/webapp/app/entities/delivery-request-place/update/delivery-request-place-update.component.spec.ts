import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DeliveryRequestPlaceFormService } from './delivery-request-place-form.service';
import { DeliveryRequestPlaceService } from '../service/delivery-request-place.service';
import { IDeliveryRequestPlace } from '../delivery-request-place.model';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';

import { DeliveryRequestPlaceUpdateComponent } from './delivery-request-place-update.component';

describe('DeliveryRequestPlace Management Update Component', () => {
  let comp: DeliveryRequestPlaceUpdateComponent;
  let fixture: ComponentFixture<DeliveryRequestPlaceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let deliveryRequestPlaceFormService: DeliveryRequestPlaceFormService;
  let deliveryRequestPlaceService: DeliveryRequestPlaceService;
  let hotelService: HotelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DeliveryRequestPlaceUpdateComponent],
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
      .overrideTemplate(DeliveryRequestPlaceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeliveryRequestPlaceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    deliveryRequestPlaceFormService = TestBed.inject(DeliveryRequestPlaceFormService);
    deliveryRequestPlaceService = TestBed.inject(DeliveryRequestPlaceService);
    hotelService = TestBed.inject(HotelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Hotel query and add missing value', () => {
      const deliveryRequestPlace: IDeliveryRequestPlace = { id: 456 };
      const hotel: IHotel = { id: 40105 };
      deliveryRequestPlace.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 41834 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ deliveryRequestPlace });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining)
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const deliveryRequestPlace: IDeliveryRequestPlace = { id: 456 };
      const hotel: IHotel = { id: 18899 };
      deliveryRequestPlace.hotel = hotel;

      activatedRoute.data = of({ deliveryRequestPlace });
      comp.ngOnInit();

      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.deliveryRequestPlace).toEqual(deliveryRequestPlace);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeliveryRequestPlace>>();
      const deliveryRequestPlace = { id: 123 };
      jest.spyOn(deliveryRequestPlaceFormService, 'getDeliveryRequestPlace').mockReturnValue(deliveryRequestPlace);
      jest.spyOn(deliveryRequestPlaceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deliveryRequestPlace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deliveryRequestPlace }));
      saveSubject.complete();

      // THEN
      expect(deliveryRequestPlaceFormService.getDeliveryRequestPlace).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(deliveryRequestPlaceService.update).toHaveBeenCalledWith(expect.objectContaining(deliveryRequestPlace));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeliveryRequestPlace>>();
      const deliveryRequestPlace = { id: 123 };
      jest.spyOn(deliveryRequestPlaceFormService, 'getDeliveryRequestPlace').mockReturnValue({ id: null });
      jest.spyOn(deliveryRequestPlaceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deliveryRequestPlace: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deliveryRequestPlace }));
      saveSubject.complete();

      // THEN
      expect(deliveryRequestPlaceFormService.getDeliveryRequestPlace).toHaveBeenCalled();
      expect(deliveryRequestPlaceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeliveryRequestPlace>>();
      const deliveryRequestPlace = { id: 123 };
      jest.spyOn(deliveryRequestPlaceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deliveryRequestPlace });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(deliveryRequestPlaceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHotel', () => {
      it('Should forward to hotelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelService, 'compareHotel');
        comp.compareHotel(entity, entity2);
        expect(hotelService.compareHotel).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
