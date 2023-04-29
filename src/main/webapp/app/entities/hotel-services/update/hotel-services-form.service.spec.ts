import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hotel-services.test-samples';

import { HotelServicesFormService } from './hotel-services-form.service';

describe('HotelServices Form Service', () => {
  let service: HotelServicesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelServicesFormService);
  });

  describe('Service methods', () => {
    describe('createHotelServicesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHotelServicesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            active: expect.any(Object),
            forGuest: expect.any(Object),
            servicePrice: expect.any(Object),
            hotel: expect.any(Object),
            services: expect.any(Object),
          })
        );
      });

      it('passing IHotelServices should create a new form with FormGroup', () => {
        const formGroup = service.createHotelServicesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            active: expect.any(Object),
            forGuest: expect.any(Object),
            servicePrice: expect.any(Object),
            hotel: expect.any(Object),
            services: expect.any(Object),
          })
        );
      });
    });

    describe('getHotelServices', () => {
      it('should return NewHotelServices for default HotelServices initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHotelServicesFormGroup(sampleWithNewData);

        const hotelServices = service.getHotelServices(formGroup) as any;

        expect(hotelServices).toMatchObject(sampleWithNewData);
      });

      it('should return NewHotelServices for empty HotelServices initial value', () => {
        const formGroup = service.createHotelServicesFormGroup();

        const hotelServices = service.getHotelServices(formGroup) as any;

        expect(hotelServices).toMatchObject({});
      });

      it('should return IHotelServices', () => {
        const formGroup = service.createHotelServicesFormGroup(sampleWithRequiredData);

        const hotelServices = service.getHotelServices(formGroup) as any;

        expect(hotelServices).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHotelServices should not enable id FormControl', () => {
        const formGroup = service.createHotelServicesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHotelServices should disable id FormControl', () => {
        const formGroup = service.createHotelServicesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
