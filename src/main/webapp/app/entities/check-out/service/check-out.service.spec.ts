import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckOut } from '../check-out.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../check-out.test-samples';

import { CheckOutService, RestCheckOut } from './check-out.service';

const requireRestSample: RestCheckOut = {
  ...sampleWithRequiredData,
  lateCheckOut: sampleWithRequiredData.lateCheckOut?.toJSON(),
};

describe('CheckOut Service', () => {
  let service: CheckOutService;
  let httpMock: HttpTestingController;
  let expectedResult: ICheckOut | ICheckOut[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CheckOutService);
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

    it('should create a CheckOut', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const checkOut = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(checkOut).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CheckOut', () => {
      const checkOut = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(checkOut).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CheckOut', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CheckOut', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CheckOut', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCheckOutToCollectionIfMissing', () => {
      it('should add a CheckOut to an empty array', () => {
        const checkOut: ICheckOut = sampleWithRequiredData;
        expectedResult = service.addCheckOutToCollectionIfMissing([], checkOut);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkOut);
      });

      it('should not add a CheckOut to an array that contains it', () => {
        const checkOut: ICheckOut = sampleWithRequiredData;
        const checkOutCollection: ICheckOut[] = [
          {
            ...checkOut,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, checkOut);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CheckOut to an array that doesn't contain it", () => {
        const checkOut: ICheckOut = sampleWithRequiredData;
        const checkOutCollection: ICheckOut[] = [sampleWithPartialData];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, checkOut);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkOut);
      });

      it('should add only unique CheckOut to an array', () => {
        const checkOutArray: ICheckOut[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const checkOutCollection: ICheckOut[] = [sampleWithRequiredData];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, ...checkOutArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const checkOut: ICheckOut = sampleWithRequiredData;
        const checkOut2: ICheckOut = sampleWithPartialData;
        expectedResult = service.addCheckOutToCollectionIfMissing([], checkOut, checkOut2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkOut);
        expect(expectedResult).toContain(checkOut2);
      });

      it('should accept null and undefined values', () => {
        const checkOut: ICheckOut = sampleWithRequiredData;
        expectedResult = service.addCheckOutToCollectionIfMissing([], null, checkOut, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkOut);
      });

      it('should return initial array if no CheckOut is added', () => {
        const checkOutCollection: ICheckOut[] = [sampleWithRequiredData];
        expectedResult = service.addCheckOutToCollectionIfMissing(checkOutCollection, undefined, null);
        expect(expectedResult).toEqual(checkOutCollection);
      });
    });

    describe('compareCheckOut', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCheckOut(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCheckOut(entity1, entity2);
        const compareResult2 = service.compareCheckOut(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCheckOut(entity1, entity2);
        const compareResult2 = service.compareCheckOut(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCheckOut(entity1, entity2);
        const compareResult2 = service.compareCheckOut(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
