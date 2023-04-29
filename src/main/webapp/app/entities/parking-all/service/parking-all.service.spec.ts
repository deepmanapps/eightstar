import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IParkingAll } from '../parking-all.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../parking-all.test-samples';

import { ParkingAllService } from './parking-all.service';

const requireRestSample: IParkingAll = {
  ...sampleWithRequiredData,
};

describe('ParkingAll Service', () => {
  let service: ParkingAllService;
  let httpMock: HttpTestingController;
  let expectedResult: IParkingAll | IParkingAll[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ParkingAllService);
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

    it('should create a ParkingAll', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const parkingAll = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(parkingAll).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ParkingAll', () => {
      const parkingAll = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(parkingAll).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ParkingAll', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ParkingAll', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ParkingAll', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addParkingAllToCollectionIfMissing', () => {
      it('should add a ParkingAll to an empty array', () => {
        const parkingAll: IParkingAll = sampleWithRequiredData;
        expectedResult = service.addParkingAllToCollectionIfMissing([], parkingAll);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parkingAll);
      });

      it('should not add a ParkingAll to an array that contains it', () => {
        const parkingAll: IParkingAll = sampleWithRequiredData;
        const parkingAllCollection: IParkingAll[] = [
          {
            ...parkingAll,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addParkingAllToCollectionIfMissing(parkingAllCollection, parkingAll);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ParkingAll to an array that doesn't contain it", () => {
        const parkingAll: IParkingAll = sampleWithRequiredData;
        const parkingAllCollection: IParkingAll[] = [sampleWithPartialData];
        expectedResult = service.addParkingAllToCollectionIfMissing(parkingAllCollection, parkingAll);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parkingAll);
      });

      it('should add only unique ParkingAll to an array', () => {
        const parkingAllArray: IParkingAll[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const parkingAllCollection: IParkingAll[] = [sampleWithRequiredData];
        expectedResult = service.addParkingAllToCollectionIfMissing(parkingAllCollection, ...parkingAllArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const parkingAll: IParkingAll = sampleWithRequiredData;
        const parkingAll2: IParkingAll = sampleWithPartialData;
        expectedResult = service.addParkingAllToCollectionIfMissing([], parkingAll, parkingAll2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(parkingAll);
        expect(expectedResult).toContain(parkingAll2);
      });

      it('should accept null and undefined values', () => {
        const parkingAll: IParkingAll = sampleWithRequiredData;
        expectedResult = service.addParkingAllToCollectionIfMissing([], null, parkingAll, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(parkingAll);
      });

      it('should return initial array if no ParkingAll is added', () => {
        const parkingAllCollection: IParkingAll[] = [sampleWithRequiredData];
        expectedResult = service.addParkingAllToCollectionIfMissing(parkingAllCollection, undefined, null);
        expect(expectedResult).toEqual(parkingAllCollection);
      });
    });

    describe('compareParkingAll', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareParkingAll(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareParkingAll(entity1, entity2);
        const compareResult2 = service.compareParkingAll(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareParkingAll(entity1, entity2);
        const compareResult2 = service.compareParkingAll(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareParkingAll(entity1, entity2);
        const compareResult2 = service.compareParkingAll(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
