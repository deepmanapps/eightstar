import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ServicesFormService } from './services-form.service';
import { ServicesService } from '../service/services.service';
import { IServices } from '../services.model';

import { ServicesUpdateComponent } from './services-update.component';

describe('Services Management Update Component', () => {
  let comp: ServicesUpdateComponent;
  let fixture: ComponentFixture<ServicesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let servicesFormService: ServicesFormService;
  let servicesService: ServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ServicesUpdateComponent],
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
      .overrideTemplate(ServicesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServicesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    servicesFormService = TestBed.inject(ServicesFormService);
    servicesService = TestBed.inject(ServicesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Services query and add missing value', () => {
      const services: IServices = { id: 456 };
      const parentService: IServices = { id: 98831 };
      services.parentService = parentService;

      const servicesCollection: IServices[] = [{ id: 39598 }];
      jest.spyOn(servicesService, 'query').mockReturnValue(of(new HttpResponse({ body: servicesCollection })));
      const additionalServices = [parentService];
      const expectedCollection: IServices[] = [...additionalServices, ...servicesCollection];
      jest.spyOn(servicesService, 'addServicesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ services });
      comp.ngOnInit();

      expect(servicesService.query).toHaveBeenCalled();
      expect(servicesService.addServicesToCollectionIfMissing).toHaveBeenCalledWith(
        servicesCollection,
        ...additionalServices.map(expect.objectContaining)
      );
      expect(comp.servicesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const services: IServices = { id: 456 };
      const parentService: IServices = { id: 3540 };
      services.parentService = parentService;

      activatedRoute.data = of({ services });
      comp.ngOnInit();

      expect(comp.servicesSharedCollection).toContain(parentService);
      expect(comp.services).toEqual(services);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServices>>();
      const services = { id: 123 };
      jest.spyOn(servicesFormService, 'getServices').mockReturnValue(services);
      jest.spyOn(servicesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ services });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: services }));
      saveSubject.complete();

      // THEN
      expect(servicesFormService.getServices).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(servicesService.update).toHaveBeenCalledWith(expect.objectContaining(services));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServices>>();
      const services = { id: 123 };
      jest.spyOn(servicesFormService, 'getServices').mockReturnValue({ id: null });
      jest.spyOn(servicesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ services: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: services }));
      saveSubject.complete();

      // THEN
      expect(servicesFormService.getServices).toHaveBeenCalled();
      expect(servicesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IServices>>();
      const services = { id: 123 };
      jest.spyOn(servicesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ services });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(servicesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareServices', () => {
      it('Should forward to servicesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(servicesService, 'compareServices');
        comp.compareServices(entity, entity2);
        expect(servicesService.compareServices).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
