import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ServiceRequestFormService } from './service-request-form.service';
import { ServiceRequestService } from '../service/service-request.service';
import { IServiceRequest } from '../service-request.model';
import { IParkingAll } from 'app/entities/parking-all/parking-all.model';
import { ParkingAllService } from 'app/entities/parking-all/service/parking-all.service';
import { IDeliveryRequestPlace } from 'app/entities/delivery-request-place/delivery-request-place.model';
import { DeliveryRequestPlaceService } from 'app/entities/delivery-request-place/service/delivery-request-place.service';
import { IServices } from 'app/entities/services/services.model';
import { ServicesService } from 'app/entities/services/service/services.service';
import { ICheckIn } from 'app/entities/check-in/check-in.model';
import { CheckInService } from 'app/entities/check-in/service/check-in.service';

import { ServiceRequestUpdateComponent } from './service-request-update.component';

describe('ServiceRequest Management Update Component', () => {
  let comp: ServiceRequestUpdateComponent;
  let fixture: ComponentFixture<ServiceRequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let serviceRequestFormService: ServiceRequestFormService;
  let serviceRequestService: ServiceRequestService;
  let parkingAllService: ParkingAllService;
  let deliveryRequestPlaceService: DeliveryRequestPlaceService;
  let servicesService: ServicesService;
  let checkInService: CheckInService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ServiceRequestUpdateComponent],
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
      .overrideTemplate(ServiceRequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    serviceRequestFormService = TestBed.inject(ServiceRequestFormService);
    serviceRequestService = TestBed.inject(ServiceRequestService);
    parkingAllService = TestBed.inject(ParkingAllService);
    deliveryRequestPlaceService = TestBed.inject(DeliveryRequestPlaceService);
    servicesService = TestBed.inject(ServicesService);
    checkInService = TestBed.inject(CheckInService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call parkingAll query and add missing value', () => {
      const serviceRequest: IServiceRequest = { id: 456 };
      const parkingAll: IParkingAll = { id: 23784 };
      serviceRequest.parkingAll = parkingAll;

      const parkingAllCollection: IParkingAll[] = [{ id: 88901 }];
      jest.spyOn(parkingAllService, 'query').mockReturnValue(of(new HttpResponse({ body: parkingAllCollection })));
      const expectedCollection: IParkingAll[] = [parkingAll, ...parkingAllCollection];
      jest.spyOn(parkingAllService, 'addParkingAllToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      expect(parkingAllService.query).toHaveBeenCalled();
      expect(parkingAllService.addParkingAllToCollectionIfMissing).toHaveBeenCalledWith(parkingAllCollection, parkingAll);
      expect(comp.parkingAllsCollection).toEqual(expectedCollection);
    });

    it('Should call deliveryRequestPlace query and add missing value', () => {
      const serviceRequest: IServiceRequest = { id: 456 };
      const deliveryRequestPlace: IDeliveryRequestPlace = { id: 93915 };
      serviceRequest.deliveryRequestPlace = deliveryRequestPlace;

      const deliveryRequestPlaceCollection: IDeliveryRequestPlace[] = [{ id: 20223 }];
      jest.spyOn(deliveryRequestPlaceService, 'query').mockReturnValue(of(new HttpResponse({ body: deliveryRequestPlaceCollection })));
      const expectedCollection: IDeliveryRequestPlace[] = [deliveryRequestPlace, ...deliveryRequestPlaceCollection];
      jest.spyOn(deliveryRequestPlaceService, 'addDeliveryRequestPlaceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      expect(deliveryRequestPlaceService.query).toHaveBeenCalled();
      expect(deliveryRequestPlaceService.addDeliveryRequestPlaceToCollectionIfMissing).toHaveBeenCalledWith(
        deliveryRequestPlaceCollection,
        deliveryRequestPlace
      );
      expect(comp.deliveryRequestPlacesCollection).toEqual(expectedCollection);
    });

    it('Should call Services query and add missing value', () => {
      const serviceRequest: IServiceRequest = { id: 456 };
      const services: IServices = { id: 63932 };
      serviceRequest.services = services;

      const servicesCollection: IServices[] = [{ id: 32968 }];
      jest.spyOn(servicesService, 'query').mockReturnValue(of(new HttpResponse({ body: servicesCollection })));
      const additionalServices = [services];
      const expectedCollection: IServices[] = [...additionalServices, ...servicesCollection];
      jest.spyOn(servicesService, 'addServicesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      expect(servicesService.query).toHaveBeenCalled();
      expect(servicesService.addServicesToCollectionIfMissing).toHaveBeenCalledWith(
        servicesCollection,
        ...additionalServices.map(expect.objectContaining)
      );
      expect(comp.servicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CheckIn query and add missing value', () => {
      const serviceRequest: IServiceRequest = { id: 456 };
      const checkIn: ICheckIn = { id: 97360 };
      serviceRequest.checkIn = checkIn;

      const checkInCollection: ICheckIn[] = [{ id: 79384 }];
      jest.spyOn(checkInService, 'query').mockReturnValue(of(new HttpResponse({ body: checkInCollection })));
      const additionalCheckIns = [checkIn];
      const expectedCollection: ICheckIn[] = [...additionalCheckIns, ...checkInCollection];
      jest.spyOn(checkInService, 'addCheckInToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      expect(checkInService.query).toHaveBeenCalled();
      expect(checkInService.addCheckInToCollectionIfMissing).toHaveBeenCalledWith(
        checkInCollection,
        ...additionalCheckIns.map(expect.objectContaining)
      );
      expect(comp.checkInsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const serviceRequest: IServiceRequest = { id: 456 };
      const parkingAll: IParkingAll = { id: 1923 };
      serviceRequest.parkingAll = parkingAll;
      const deliveryRequestPlace: IDeliveryRequestPlace = { id: 89425 };
      serviceRequest.deliveryRequestPlace = deliveryRequestPlace;
      const services: IServices = { id: 55252 };
      serviceRequest.services = services;
      const checkIn: ICheckIn = { id: 43456 };
      serviceRequest.checkIn = checkIn;

      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      expect(comp.parkingAllsCollection).toContain(parkingAll);
      expect(comp.deliveryRequestPlacesCollection).toContain(deliveryRequestPlace);
      expect(comp.servicesSharedCollection).toContain(services);
      expect(comp.checkInsSharedCollection).toContain(checkIn);
      expect(comp.serviceRequest).toEqual(serviceRequest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceRequest>>();
      const serviceRequest = { id: 123 };
      jest.spyOn(serviceRequestFormService, 'getServiceRequest').mockReturnValue(serviceRequest);
      jest.spyOn(serviceRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serviceRequest }));
      saveSubject.complete();

      // THEN
      expect(serviceRequestFormService.getServiceRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(serviceRequestService.update).toHaveBeenCalledWith(expect.objectContaining(serviceRequest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceRequest>>();
      const serviceRequest = { id: 123 };
      jest.spyOn(serviceRequestFormService, 'getServiceRequest').mockReturnValue({ id: null });
      jest.spyOn(serviceRequestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceRequest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: serviceRequest }));
      saveSubject.complete();

      // THEN
      expect(serviceRequestFormService.getServiceRequest).toHaveBeenCalled();
      expect(serviceRequestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServiceRequest>>();
      const serviceRequest = { id: 123 };
      jest.spyOn(serviceRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ serviceRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(serviceRequestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareParkingAll', () => {
      it('Should forward to parkingAllService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(parkingAllService, 'compareParkingAll');
        comp.compareParkingAll(entity, entity2);
        expect(parkingAllService.compareParkingAll).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDeliveryRequestPlace', () => {
      it('Should forward to deliveryRequestPlaceService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(deliveryRequestPlaceService, 'compareDeliveryRequestPlace');
        comp.compareDeliveryRequestPlace(entity, entity2);
        expect(deliveryRequestPlaceService.compareDeliveryRequestPlace).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareCheckIn', () => {
      it('Should forward to checkInService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(checkInService, 'compareCheckIn');
        comp.compareCheckIn(entity, entity2);
        expect(checkInService.compareCheckIn).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
