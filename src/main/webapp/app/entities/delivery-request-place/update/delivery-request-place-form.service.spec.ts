import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../delivery-request-place.test-samples';

import { DeliveryRequestPlaceFormService } from './delivery-request-place-form.service';

describe('DeliveryRequestPlace Form Service', () => {
  let service: DeliveryRequestPlaceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeliveryRequestPlaceFormService);
  });

  describe('Service methods', () => {
    describe('createDeliveryRequestPlaceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDeliveryRequestPlaceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            hotel: expect.any(Object),
          })
        );
      });

      it('passing IDeliveryRequestPlace should create a new form with FormGroup', () => {
        const formGroup = service.createDeliveryRequestPlaceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            hotel: expect.any(Object),
          })
        );
      });
    });

    describe('getDeliveryRequestPlace', () => {
      it('should return NewDeliveryRequestPlace for default DeliveryRequestPlace initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDeliveryRequestPlaceFormGroup(sampleWithNewData);

        const deliveryRequestPlace = service.getDeliveryRequestPlace(formGroup) as any;

        expect(deliveryRequestPlace).toMatchObject(sampleWithNewData);
      });

      it('should return NewDeliveryRequestPlace for empty DeliveryRequestPlace initial value', () => {
        const formGroup = service.createDeliveryRequestPlaceFormGroup();

        const deliveryRequestPlace = service.getDeliveryRequestPlace(formGroup) as any;

        expect(deliveryRequestPlace).toMatchObject({});
      });

      it('should return IDeliveryRequestPlace', () => {
        const formGroup = service.createDeliveryRequestPlaceFormGroup(sampleWithRequiredData);

        const deliveryRequestPlace = service.getDeliveryRequestPlace(formGroup) as any;

        expect(deliveryRequestPlace).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDeliveryRequestPlace should not enable id FormControl', () => {
        const formGroup = service.createDeliveryRequestPlaceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDeliveryRequestPlace should disable id FormControl', () => {
        const formGroup = service.createDeliveryRequestPlaceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
