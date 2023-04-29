import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductRequestFormService } from './product-request-form.service';
import { ProductRequestService } from '../service/product-request.service';
import { IProductRequest } from '../product-request.model';
import { IServiceRequest } from 'app/entities/service-request/service-request.model';
import { ServiceRequestService } from 'app/entities/service-request/service/service-request.service';

import { ProductRequestUpdateComponent } from './product-request-update.component';

describe('ProductRequest Management Update Component', () => {
  let comp: ProductRequestUpdateComponent;
  let fixture: ComponentFixture<ProductRequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productRequestFormService: ProductRequestFormService;
  let productRequestService: ProductRequestService;
  let serviceRequestService: ServiceRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductRequestUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProductRequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductRequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productRequestFormService = TestBed.inject(ProductRequestFormService);
    productRequestService = TestBed.inject(ProductRequestService);
    serviceRequestService = TestBed.inject(ServiceRequestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ServiceRequest query and add missing value', () => {
      const productRequest: IProductRequest = { id: 456 };
      const serviceRequest: IServiceRequest = { id: 47476 };
      productRequest.serviceRequest = serviceRequest;

      const serviceRequestCollection: IServiceRequest[] = [{ id: 78177 }];
      jest.spyOn(serviceRequestService, 'query').mockReturnValue(of(new HttpResponse({ body: serviceRequestCollection })));
      const additionalServiceRequests = [serviceRequest];
      const expectedCollection: IServiceRequest[] = [...additionalServiceRequests, ...serviceRequestCollection];
      jest.spyOn(serviceRequestService, 'addServiceRequestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productRequest });
      comp.ngOnInit();

      expect(serviceRequestService.query).toHaveBeenCalled();
      expect(serviceRequestService.addServiceRequestToCollectionIfMissing).toHaveBeenCalledWith(
        serviceRequestCollection,
        ...additionalServiceRequests.map(expect.objectContaining)
      );
      expect(comp.serviceRequestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productRequest: IProductRequest = { id: 456 };
      const serviceRequest: IServiceRequest = { id: 99908 };
      productRequest.serviceRequest = serviceRequest;

      activatedRoute.data = of({ productRequest });
      comp.ngOnInit();

      expect(comp.serviceRequestsSharedCollection).toContain(serviceRequest);
      expect(comp.productRequest).toEqual(productRequest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductRequest>>();
      const productRequest = { id: 123 };
      jest.spyOn(productRequestFormService, 'getProductRequest').mockReturnValue(productRequest);
      jest.spyOn(productRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productRequest }));
      saveSubject.complete();

      // THEN
      expect(productRequestFormService.getProductRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productRequestService.update).toHaveBeenCalledWith(expect.objectContaining(productRequest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductRequest>>();
      const productRequest = { id: 123 };
      jest.spyOn(productRequestFormService, 'getProductRequest').mockReturnValue({ id: null });
      jest.spyOn(productRequestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRequest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productRequest }));
      saveSubject.complete();

      // THEN
      expect(productRequestFormService.getProductRequest).toHaveBeenCalled();
      expect(productRequestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductRequest>>();
      const productRequest = { id: 123 };
      jest.spyOn(productRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productRequestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServiceRequest', () => {
      it('Should forward to serviceRequestService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(serviceRequestService, 'compareServiceRequest');
        comp.compareServiceRequest(entity, entity2);
        expect(serviceRequestService.compareServiceRequest).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
