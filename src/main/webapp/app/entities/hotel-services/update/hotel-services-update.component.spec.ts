import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HotelServicesFormService } from './hotel-services-form.service';
import { HotelServicesService } from '../service/hotel-services.service';
import { IHotelServices } from '../hotel-services.model';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';
import { IServices } from 'app/entities/services/services.model';
import { ServicesService } from 'app/entities/services/service/services.service';

import { HotelServicesUpdateComponent } from './hotel-services-update.component';

describe('HotelServices Management Update Component', () => {
  let comp: HotelServicesUpdateComponent;
  let fixture: ComponentFixture<HotelServicesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hotelServicesFormService: HotelServicesFormService;
  let hotelServicesService: HotelServicesService;
  let hotelService: HotelService;
  let servicesService: ServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HotelServicesUpdateComponent],
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
      .overrideTemplate(HotelServicesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelServicesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hotelServicesFormService = TestBed.inject(HotelServicesFormService);
    hotelServicesService = TestBed.inject(HotelServicesService);
    hotelService = TestBed.inject(HotelService);
    servicesService = TestBed.inject(ServicesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Hotel query and add missing value', () => {
      const hotelServices: IHotelServices = { id: 456 };
      const hotel: IHotel = { id: 84877 };
      hotelServices.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 44630 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelServices });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining)
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Services query and add missing value', () => {
      const hotelServices: IHotelServices = { id: 456 };
      const services: IServices = { id: 19059 };
      hotelServices.services = services;

      const servicesCollection: IServices[] = [{ id: 48988 }];
      jest.spyOn(servicesService, 'query').mockReturnValue(of(new HttpResponse({ body: servicesCollection })));
      const additionalServices = [services];
      const expectedCollection: IServices[] = [...additionalServices, ...servicesCollection];
      jest.spyOn(servicesService, 'addServicesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotelServices });
      comp.ngOnInit();

      expect(servicesService.query).toHaveBeenCalled();
      expect(servicesService.addServicesToCollectionIfMissing).toHaveBeenCalledWith(
        servicesCollection,
        ...additionalServices.map(expect.objectContaining)
      );
      expect(comp.servicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hotelServices: IHotelServices = { id: 456 };
      const hotel: IHotel = { id: 24172 };
      hotelServices.hotel = hotel;
      const services: IServices = { id: 14699 };
      hotelServices.services = services;

      activatedRoute.data = of({ hotelServices });
      comp.ngOnInit();

      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.servicesSharedCollection).toContain(services);
      expect(comp.hotelServices).toEqual(hotelServices);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelServices>>();
      const hotelServices = { id: 123 };
      jest.spyOn(hotelServicesFormService, 'getHotelServices').mockReturnValue(hotelServices);
      jest.spyOn(hotelServicesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelServices });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelServices }));
      saveSubject.complete();

      // THEN
      expect(hotelServicesFormService.getHotelServices).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hotelServicesService.update).toHaveBeenCalledWith(expect.objectContaining(hotelServices));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelServices>>();
      const hotelServices = { id: 123 };
      jest.spyOn(hotelServicesFormService, 'getHotelServices').mockReturnValue({ id: null });
      jest.spyOn(hotelServicesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelServices: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotelServices }));
      saveSubject.complete();

      // THEN
      expect(hotelServicesFormService.getHotelServices).toHaveBeenCalled();
      expect(hotelServicesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotelServices>>();
      const hotelServices = { id: 123 };
      jest.spyOn(hotelServicesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotelServices });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hotelServicesService.update).toHaveBeenCalled();
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

    describe('compareServices', () => {
      it('Should forward to servicesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicesService, 'compareServices');
        comp.compareServices(entity, entity2);
        expect(servicesService.compareServices).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
