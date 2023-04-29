import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDeliveryRequestPlace } from '../delivery-request-place.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../delivery-request-place.test-samples';

import { DeliveryRequestPlaceService } from './delivery-request-place.service';

const requireRestSample: IDeliveryRequestPlace = {
  ...sampleWithRequiredData,
};

describe('DeliveryRequestPlace Service', () => {
  let service: DeliveryRequestPlaceService;
  let httpMock: HttpTestingController;
  let expectedResult: IDeliveryRequestPlace | IDeliveryRequestPlace[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DeliveryRequestPlaceService);
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

    it('should create a DeliveryRequestPlace', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const deliveryRequestPlace = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(deliveryRequestPlace).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DeliveryRequestPlace', () => {
      const deliveryRequestPlace = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(deliveryRequestPlace).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DeliveryRequestPlace', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DeliveryRequestPlace', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DeliveryRequestPlace', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDeliveryRequestPlaceToCollectionIfMissing', () => {
      it('should add a DeliveryRequestPlace to an empty array', () => {
        const deliveryRequestPlace: IDeliveryRequestPlace = sampleWithRequiredData;
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing([], deliveryRequestPlace);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deliveryRequestPlace);
      });

      it('should not add a DeliveryRequestPlace to an array that contains it', () => {
        const deliveryRequestPlace: IDeliveryRequestPlace = sampleWithRequiredData;
        const deliveryRequestPlaceCollection: IDeliveryRequestPlace[] = [
          {
            ...deliveryRequestPlace,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing(deliveryRequestPlaceCollection, deliveryRequestPlace);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DeliveryRequestPlace to an array that doesn't contain it", () => {
        const deliveryRequestPlace: IDeliveryRequestPlace = sampleWithRequiredData;
        const deliveryRequestPlaceCollection: IDeliveryRequestPlace[] = [sampleWithPartialData];
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing(deliveryRequestPlaceCollection, deliveryRequestPlace);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deliveryRequestPlace);
      });

      it('should add only unique DeliveryRequestPlace to an array', () => {
        const deliveryRequestPlaceArray: IDeliveryRequestPlace[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const deliveryRequestPlaceCollection: IDeliveryRequestPlace[] = [sampleWithRequiredData];
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing(deliveryRequestPlaceCollection, ...deliveryRequestPlaceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const deliveryRequestPlace: IDeliveryRequestPlace = sampleWithRequiredData;
        const deliveryRequestPlace2: IDeliveryRequestPlace = sampleWithPartialData;
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing([], deliveryRequestPlace, deliveryRequestPlace2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deliveryRequestPlace);
        expect(expectedResult).toContain(deliveryRequestPlace2);
      });

      it('should accept null and undefined values', () => {
        const deliveryRequestPlace: IDeliveryRequestPlace = sampleWithRequiredData;
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing([], null, deliveryRequestPlace, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deliveryRequestPlace);
      });

      it('should return initial array if no DeliveryRequestPlace is added', () => {
        const deliveryRequestPlaceCollection: IDeliveryRequestPlace[] = [sampleWithRequiredData];
        expectedResult = service.addDeliveryRequestPlaceToCollectionIfMissing(deliveryRequestPlaceCollection, undefined, null);
        expect(expectedResult).toEqual(deliveryRequestPlaceCollection);
      });
    });

    describe('compareDeliveryRequestPlace', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDeliveryRequestPlace(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDeliveryRequestPlace(entity1, entity2);
        const compareResult2 = service.compareDeliveryRequestPlace(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDeliveryRequestPlace(entity1, entity2);
        const compareResult2 = service.compareDeliveryRequestPlace(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDeliveryRequestPlace(entity1, entity2);
        const compareResult2 = service.compareDeliveryRequestPlace(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
