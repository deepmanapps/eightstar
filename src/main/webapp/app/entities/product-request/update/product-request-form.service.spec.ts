import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-request.test-samples';

import { ProductRequestFormService } from './product-request-form.service';

describe('ProductRequest Form Service', () => {
  let service: ProductRequestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductRequestFormService);
  });

  describe('Service methods', () => {
    describe('createProductRequestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductRequestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            productUnitPrice: expect.any(Object),
            productTotalPrice: expect.any(Object),
            requestedQuantity: expect.any(Object),
            serviceRequest: expect.any(Object),
          })
        );
      });

      it('passing IProductRequest should create a new form with FormGroup', () => {
        const formGroup = service.createProductRequestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            productUnitPrice: expect.any(Object),
            productTotalPrice: expect.any(Object),
            requestedQuantity: expect.any(Object),
            serviceRequest: expect.any(Object),
          })
        );
      });
    });

    describe('getProductRequest', () => {
      it('should return NewProductRequest for default ProductRequest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductRequestFormGroup(sampleWithNewData);

        const productRequest = service.getProductRequest(formGroup) as any;

        expect(productRequest).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductRequest for empty ProductRequest initial value', () => {
        const formGroup = service.createProductRequestFormGroup();

        const productRequest = service.getProductRequest(formGroup) as any;

        expect(productRequest).toMatchObject({});
      });

      it('should return IProductRequest', () => {
        const formGroup = service.createProductRequestFormGroup(sampleWithRequiredData);

        const productRequest = service.getProductRequest(formGroup) as any;

        expect(productRequest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductRequest should not enable id FormControl', () => {
        const formGroup = service.createProductRequestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductRequest should disable id FormControl', () => {
        const formGroup = service.createProductRequestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
