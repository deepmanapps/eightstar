import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../check-out.test-samples';

import { CheckOutFormService } from './check-out-form.service';

describe('CheckOut Form Service', () => {
  let service: CheckOutFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckOutFormService);
  });

  describe('Service methods', () => {
    describe('createCheckOutFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCheckOutFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roomClearance: expect.any(Object),
            customerReview: expect.any(Object),
            miniBarClearance: expect.any(Object),
            lateCheckOut: expect.any(Object),
            isLate: expect.any(Object),
            isCollectedDepositAmount: expect.any(Object),
            collectedAmount: expect.any(Object),
          })
        );
      });

      it('passing ICheckOut should create a new form with FormGroup', () => {
        const formGroup = service.createCheckOutFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            roomClearance: expect.any(Object),
            customerReview: expect.any(Object),
            miniBarClearance: expect.any(Object),
            lateCheckOut: expect.any(Object),
            isLate: expect.any(Object),
            isCollectedDepositAmount: expect.any(Object),
            collectedAmount: expect.any(Object),
          })
        );
      });
    });

    describe('getCheckOut', () => {
      it('should return NewCheckOut for default CheckOut initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCheckOutFormGroup(sampleWithNewData);

        const checkOut = service.getCheckOut(formGroup) as any;

        expect(checkOut).toMatchObject(sampleWithNewData);
      });

      it('should return NewCheckOut for empty CheckOut initial value', () => {
        const formGroup = service.createCheckOutFormGroup();

        const checkOut = service.getCheckOut(formGroup) as any;

        expect(checkOut).toMatchObject({});
      });

      it('should return ICheckOut', () => {
        const formGroup = service.createCheckOutFormGroup(sampleWithRequiredData);

        const checkOut = service.getCheckOut(formGroup) as any;

        expect(checkOut).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICheckOut should not enable id FormControl', () => {
        const formGroup = service.createCheckOutFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCheckOut should disable id FormControl', () => {
        const formGroup = service.createCheckOutFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
