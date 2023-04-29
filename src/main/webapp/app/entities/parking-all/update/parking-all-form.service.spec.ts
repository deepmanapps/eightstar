import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../parking-all.test-samples';

import { ParkingAllFormService } from './parking-all-form.service';

describe('ParkingAll Form Service', () => {
  let service: ParkingAllFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParkingAllFormService);
  });

  describe('Service methods', () => {
    describe('createParkingAllFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParkingAllFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            location: expect.any(Object),
            hotel: expect.any(Object),
          })
        );
      });

      it('passing IParkingAll should create a new form with FormGroup', () => {
        const formGroup = service.createParkingAllFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            location: expect.any(Object),
            hotel: expect.any(Object),
          })
        );
      });
    });

    describe('getParkingAll', () => {
      it('should return NewParkingAll for default ParkingAll initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createParkingAllFormGroup(sampleWithNewData);

        const parkingAll = service.getParkingAll(formGroup) as any;

        expect(parkingAll).toMatchObject(sampleWithNewData);
      });

      it('should return NewParkingAll for empty ParkingAll initial value', () => {
        const formGroup = service.createParkingAllFormGroup();

        const parkingAll = service.getParkingAll(formGroup) as any;

        expect(parkingAll).toMatchObject({});
      });

      it('should return IParkingAll', () => {
        const formGroup = service.createParkingAllFormGroup(sampleWithRequiredData);

        const parkingAll = service.getParkingAll(formGroup) as any;

        expect(parkingAll).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParkingAll should not enable id FormControl', () => {
        const formGroup = service.createParkingAllFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParkingAll should disable id FormControl', () => {
        const formGroup = service.createParkingAllFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
