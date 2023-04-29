import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductRequest } from '../product-request.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../product-request.test-samples';

import { ProductRequestService } from './product-request.service';

const requireRestSample: IProductRequest = {
  ...sampleWithRequiredData,
};

describe('ProductRequest Service', () => {
  let service: ProductRequestService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductRequest | IProductRequest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductRequestService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProductRequest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productRequest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductRequest', () => {
      const productRequest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductRequest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductRequest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductRequest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductRequestToCollectionIfMissing', () => {
      it('should add a ProductRequest to an empty array', () => {
        const productRequest: IProductRequest = sampleWithRequiredData;
        expectedResult = service.addProductRequestToCollectionIfMissing([], productRequest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productRequest);
      });

      it('should not add a ProductRequest to an array that contains it', () => {
        const productRequest: IProductRequest = sampleWithRequiredData;
        const productRequestCollection: IProductRequest[] = [
          {
            ...productRequest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductRequestToCollectionIfMissing(productRequestCollection, productRequest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductRequest to an array that doesn't contain it", () => {
        const productRequest: IProductRequest = sampleWithRequiredData;
        const productRequestCollection: IProductRequest[] = [sampleWithPartialData];
        expectedResult = service.addProductRequestToCollectionIfMissing(productRequestCollection, productRequest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productRequest);
      });

      it('should add only unique ProductRequest to an array', () => {
        const productRequestArray: IProductRequest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productRequestCollection: IProductRequest[] = [sampleWithRequiredData];
        expectedResult = service.addProductRequestToCollectionIfMissing(productRequestCollection, ...productRequestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productRequest: IProductRequest = sampleWithRequiredData;
        const productRequest2: IProductRequest = sampleWithPartialData;
        expectedResult = service.addProductRequestToCollectionIfMissing([], productRequest, productRequest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productRequest);
        expect(expectedResult).toContain(productRequest2);
      });

      it('should accept null and undefined values', () => {
        const productRequest: IProductRequest = sampleWithRequiredData;
        expectedResult = service.addProductRequestToCollectionIfMissing([], null, productRequest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productRequest);
      });

      it('should return initial array if no ProductRequest is added', () => {
        const productRequestCollection: IProductRequest[] = [sampleWithRequiredData];
        expectedResult = service.addProductRequestToCollectionIfMissing(productRequestCollection, undefined, null);
        expect(expectedResult).toEqual(productRequestCollection);
      });
    });

    describe('compareProductRequest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductRequest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductRequest(entity1, entity2);
        const compareResult2 = service.compareProductRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductRequest(entity1, entity2);
        const compareResult2 = service.compareProductRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductRequest(entity1, entity2);
        const compareResult2 = service.compareProductRequest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
