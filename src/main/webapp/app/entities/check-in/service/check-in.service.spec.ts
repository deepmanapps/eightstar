import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICheckIn } from '../check-in.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../check-in.test-samples';

import { CheckInService, RestCheckIn } from './check-in.service';

const requireRestSample: RestCheckIn = {
  ...sampleWithRequiredData,
  arrivalDate: sampleWithRequiredData.arrivalDate?.toJSON(),
  departureDate: sampleWithRequiredData.departureDate?.toJSON(),
};

describe('CheckIn Service', () => {
  let service: CheckInService;
  let httpMock: HttpTestingController;
  let expectedResult: ICheckIn | ICheckIn[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CheckInService);
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

    it('should create a CheckIn', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const checkIn = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(checkIn).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CheckIn', () => {
      const checkIn = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(checkIn).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CheckIn', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CheckIn', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CheckIn', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCheckInToCollectionIfMissing', () => {
      it('should add a CheckIn to an empty array', () => {
        const checkIn: ICheckIn = sampleWithRequiredData;
        expectedResult = service.addCheckInToCollectionIfMissing([], checkIn);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkIn);
      });

      it('should not add a CheckIn to an array that contains it', () => {
        const checkIn: ICheckIn = sampleWithRequiredData;
        const checkInCollection: ICheckIn[] = [
          {
            ...checkIn,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCheckInToCollectionIfMissing(checkInCollection, checkIn);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CheckIn to an array that doesn't contain it", () => {
        const checkIn: ICheckIn = sampleWithRequiredData;
        const checkInCollection: ICheckIn[] = [sampleWithPartialData];
        expectedResult = service.addCheckInToCollectionIfMissing(checkInCollection, checkIn);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkIn);
      });

      it('should add only unique CheckIn to an array', () => {
        const checkInArray: ICheckIn[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const checkInCollection: ICheckIn[] = [sampleWithRequiredData];
        expectedResult = service.addCheckInToCollectionIfMissing(checkInCollection, ...checkInArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const checkIn: ICheckIn = sampleWithRequiredData;
        const checkIn2: ICheckIn = sampleWithPartialData;
        expectedResult = service.addCheckInToCollectionIfMissing([], checkIn, checkIn2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(checkIn);
        expect(expectedResult).toContain(checkIn2);
      });

      it('should accept null and undefined values', () => {
        const checkIn: ICheckIn = sampleWithRequiredData;
        expectedResult = service.addCheckInToCollectionIfMissing([], null, checkIn, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(checkIn);
      });

      it('should return initial array if no CheckIn is added', () => {
        const checkInCollection: ICheckIn[] = [sampleWithRequiredData];
        expectedResult = service.addCheckInToCollectionIfMissing(checkInCollection, undefined, null);
        expect(expectedResult).toEqual(checkInCollection);
      });
    });

    describe('compareCheckIn', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCheckIn(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCheckIn(entity1, entity2);
        const compareResult2 = service.compareCheckIn(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCheckIn(entity1, entity2);
        const compareResult2 = service.compareCheckIn(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCheckIn(entity1, entity2);
        const compareResult2 = service.compareCheckIn(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
