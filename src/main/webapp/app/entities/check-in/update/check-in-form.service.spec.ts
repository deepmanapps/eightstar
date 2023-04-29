import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../check-in.test-samples';

import { CheckInFormService } from './check-in-form.service';

describe('CheckIn Form Service', () => {
  let service: CheckInFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheckInFormService);
  });

  describe('Service methods', () => {
    describe('createCheckInFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCheckInFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            identityPath: expect.any(Object),
            status: expect.any(Object),
            depositAmount: expect.any(Object),
            paymentMethod: expect.any(Object),
            arrivalDate: expect.any(Object),
            departureDate: expect.any(Object),
            roomType: expect.any(Object),
            smooking: expect.any(Object),
            adults: expect.any(Object),
            children: expect.any(Object),
            notes: expect.any(Object),
            checkOut: expect.any(Object),
            hotel: expect.any(Object),
            customer: expect.any(Object),
          })
        );
      });

      it('passing ICheckIn should create a new form with FormGroup', () => {
        const formGroup = service.createCheckInFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            identityPath: expect.any(Object),
            status: expect.any(Object),
            depositAmount: expect.any(Object),
            paymentMethod: expect.any(Object),
            arrivalDate: expect.any(Object),
            departureDate: expect.any(Object),
            roomType: expect.any(Object),
            smooking: expect.any(Object),
            adults: expect.any(Object),
            children: expect.any(Object),
            notes: expect.any(Object),
            checkOut: expect.any(Object),
            hotel: expect.any(Object),
            customer: expect.any(Object),
          })
        );
      });
    });

    describe('getCheckIn', () => {
      it('should return NewCheckIn for default CheckIn initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCheckInFormGroup(sampleWithNewData);

        const checkIn = service.getCheckIn(formGroup) as any;

        expect(checkIn).toMatchObject(sampleWithNewData);
      });

      it('should return NewCheckIn for empty CheckIn initial value', () => {
        const formGroup = service.createCheckInFormGroup();

        const checkIn = service.getCheckIn(formGroup) as any;

        expect(checkIn).toMatchObject({});
      });

      it('should return ICheckIn', () => {
        const formGroup = service.createCheckInFormGroup(sampleWithRequiredData);

        const checkIn = service.getCheckIn(formGroup) as any;

        expect(checkIn).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICheckIn should not enable id FormControl', () => {
        const formGroup = service.createCheckInFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCheckIn should disable id FormControl', () => {
        const formGroup = service.createCheckInFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
