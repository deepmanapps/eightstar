import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IServiceRequest } from '../service-request.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../service-request.test-samples';

import { ServiceRequestService, RestServiceRequest } from './service-request.service';

const requireRestSample: RestServiceRequest = {
  ...sampleWithRequiredData,
  requestDate: sampleWithRequiredData.requestDate?.toJSON(),
  requestThruDate: sampleWithRequiredData.requestThruDate?.toJSON(),
};

describe('ServiceRequest Service', () => {
  let service: ServiceRequestService;
  let httpMock: HttpTestingController;
  let expectedResult: IServiceRequest | IServiceRequest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ServiceRequestService);
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

    it('should create a ServiceRequest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const serviceRequest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(serviceRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServiceRequest', () => {
      const serviceRequest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(serviceRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServiceRequest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServiceRequest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServiceRequest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServiceRequestToCollectionIfMissing', () => {
      it('should add a ServiceRequest to an empty array', () => {
        const serviceRequest: IServiceRequest = sampleWithRequiredData;
        expectedResult = service.addServiceRequestToCollectionIfMissing([], serviceRequest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serviceRequest);
      });

      it('should not add a ServiceRequest to an array that contains it', () => {
        const serviceRequest: IServiceRequest = sampleWithRequiredData;
        const serviceRequestCollection: IServiceRequest[] = [
          {
            ...serviceRequest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServiceRequestToCollectionIfMissing(serviceRequestCollection, serviceRequest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServiceRequest to an array that doesn't contain it", () => {
        const serviceRequest: IServiceRequest = sampleWithRequiredData;
        const serviceRequestCollection: IServiceRequest[] = [sampleWithPartialData];
        expectedResult = service.addServiceRequestToCollectionIfMissing(serviceRequestCollection, serviceRequest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serviceRequest);
      });

      it('should add only unique ServiceRequest to an array', () => {
        const serviceRequestArray: IServiceRequest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const serviceRequestCollection: IServiceRequest[] = [sampleWithRequiredData];
        expectedResult = service.addServiceRequestToCollectionIfMissing(serviceRequestCollection, ...serviceRequestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const serviceRequest: IServiceRequest = sampleWithRequiredData;
        const serviceRequest2: IServiceRequest = sampleWithPartialData;
        expectedResult = service.addServiceRequestToCollectionIfMissing([], serviceRequest, serviceRequest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serviceRequest);
        expect(expectedResult).toContain(serviceRequest2);
      });

      it('should accept null and undefined values', () => {
        const serviceRequest: IServiceRequest = sampleWithRequiredData;
        expectedResult = service.addServiceRequestToCollectionIfMissing([], null, serviceRequest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serviceRequest);
      });

      it('should return initial array if no ServiceRequest is added', () => {
        const serviceRequestCollection: IServiceRequest[] = [sampleWithRequiredData];
        expectedResult = service.addServiceRequestToCollectionIfMissing(serviceRequestCollection, undefined, null);
        expect(expectedResult).toEqual(serviceRequestCollection);
      });
    });

    describe('compareServiceRequest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServiceRequest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServiceRequest(entity1, entity2);
        const compareResult2 = service.compareServiceRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServiceRequest(entity1, entity2);
        const compareResult2 = service.compareServiceRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServiceRequest(entity1, entity2);
        const compareResult2 = service.compareServiceRequest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
