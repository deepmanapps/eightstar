import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../service-request.test-samples';

import { ServiceRequestFormService } from './service-request-form.service';

describe('ServiceRequest Form Service', () => {
  let service: ServiceRequestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceRequestFormService);
  });

  describe('Service methods', () => {
    describe('createServiceRequestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServiceRequestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestDate: expect.any(Object),
            requestThruDate: expect.any(Object),
            statusRequest: expect.any(Object),
            rejecttReason: expect.any(Object),
            requestDescription: expect.any(Object),
            objectNumber: expect.any(Object),
            guest: expect.any(Object),
            quantity: expect.any(Object),
            totalPrice: expect.any(Object),
            parkingAll: expect.any(Object),
            deliveryRequestPlace: expect.any(Object),
            services: expect.any(Object),
            checkIn: expect.any(Object),
          })
        );
      });

      it('passing IServiceRequest should create a new form with FormGroup', () => {
        const formGroup = service.createServiceRequestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestDate: expect.any(Object),
            requestThruDate: expect.any(Object),
            statusRequest: expect.any(Object),
            rejecttReason: expect.any(Object),
            requestDescription: expect.any(Object),
            objectNumber: expect.any(Object),
            guest: expect.any(Object),
            quantity: expect.any(Object),
            totalPrice: expect.any(Object),
            parkingAll: expect.any(Object),
            deliveryRequestPlace: expect.any(Object),
            services: expect.any(Object),
            checkIn: expect.any(Object),
          })
        );
      });
    });

    describe('getServiceRequest', () => {
      it('should return NewServiceRequest for default ServiceRequest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createServiceRequestFormGroup(sampleWithNewData);

        const serviceRequest = service.getServiceRequest(formGroup) as any;

        expect(serviceRequest).toMatchObject(sampleWithNewData);
      });

      it('should return NewServiceRequest for empty ServiceRequest initial value', () => {
        const formGroup = service.createServiceRequestFormGroup();

        const serviceRequest = service.getServiceRequest(formGroup) as any;

        expect(serviceRequest).toMatchObject({});
      });

      it('should return IServiceRequest', () => {
        const formGroup = service.createServiceRequestFormGroup(sampleWithRequiredData);

        const serviceRequest = service.getServiceRequest(formGroup) as any;

        expect(serviceRequest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServiceRequest should not enable id FormControl', () => {
        const formGroup = service.createServiceRequestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServiceRequest should disable id FormControl', () => {
        const formGroup = service.createServiceRequestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
