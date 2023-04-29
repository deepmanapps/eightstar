import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParkingAllFormService } from './parking-all-form.service';
import { ParkingAllService } from '../service/parking-all.service';
import { IParkingAll } from '../parking-all.model';
import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { HotelService } from 'app/entities/hotel/service/hotel.service';

import { ParkingAllUpdateComponent } from './parking-all-update.component';

describe('ParkingAll Management Update Component', () => {
  let comp: ParkingAllUpdateComponent;
  let fixture: ComponentFixture<ParkingAllUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parkingAllFormService: ParkingAllFormService;
  let parkingAllService: ParkingAllService;
  let locationService: LocationService;
  let hotelService: HotelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParkingAllUpdateComponent],
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
      .overrideTemplate(ParkingAllUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParkingAllUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parkingAllFormService = TestBed.inject(ParkingAllFormService);
    parkingAllService = TestBed.inject(ParkingAllService);
    locationService = TestBed.inject(LocationService);
    hotelService = TestBed.inject(HotelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call location query and add missing value', () => {
      const parkingAll: IParkingAll = { id: 456 };
      const location: ILocation = { id: 52318 };
      parkingAll.location = location;

      const locationCollection: ILocation[] = [{ id: 54051 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const expectedCollection: ILocation[] = [location, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parkingAll });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, location);
      expect(comp.locationsCollection).toEqual(expectedCollection);
    });

    it('Should call Hotel query and add missing value', () => {
      const parkingAll: IParkingAll = { id: 456 };
      const hotel: IHotel = { id: 53358 };
      parkingAll.hotel = hotel;

      const hotelCollection: IHotel[] = [{ id: 22896 }];
      jest.spyOn(hotelService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelCollection })));
      const additionalHotels = [hotel];
      const expectedCollection: IHotel[] = [...additionalHotels, ...hotelCollection];
      jest.spyOn(hotelService, 'addHotelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parkingAll });
      comp.ngOnInit();

      expect(hotelService.query).toHaveBeenCalled();
      expect(hotelService.addHotelToCollectionIfMissing).toHaveBeenCalledWith(
        hotelCollection,
        ...additionalHotels.map(expect.objectContaining)
      );
      expect(comp.hotelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const parkingAll: IParkingAll = { id: 456 };
      const location: ILocation = { id: 46269 };
      parkingAll.location = location;
      const hotel: IHotel = { id: 46320 };
      parkingAll.hotel = hotel;

      activatedRoute.data = of({ parkingAll });
      comp.ngOnInit();

      expect(comp.locationsCollection).toContain(location);
      expect(comp.hotelsSharedCollection).toContain(hotel);
      expect(comp.parkingAll).toEqual(parkingAll);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParkingAll>>();
      const parkingAll = { id: 123 };
      jest.spyOn(parkingAllFormService, 'getParkingAll').mockReturnValue(parkingAll);
      jest.spyOn(parkingAllService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parkingAll });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parkingAll }));
      saveSubject.complete();

      // THEN
      expect(parkingAllFormService.getParkingAll).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(parkingAllService.update).toHaveBeenCalledWith(expect.objectContaining(parkingAll));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParkingAll>>();
      const parkingAll = { id: 123 };
      jest.spyOn(parkingAllFormService, 'getParkingAll').mockReturnValue({ id: null });
      jest.spyOn(parkingAllService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parkingAll: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parkingAll }));
      saveSubject.complete();

      // THEN
      expect(parkingAllFormService.getParkingAll).toHaveBeenCalled();
      expect(parkingAllService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParkingAll>>();
      const parkingAll = { id: 123 };
      jest.spyOn(parkingAllService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parkingAll });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parkingAllService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLocation', () => {
      it('Should forward to locationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(locationService, 'compareLocation');
        comp.compareLocation(entity, entity2);
        expect(locationService.compareLocation).toHaveBeenCalledWith(entity, entity2);
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
  });
});
