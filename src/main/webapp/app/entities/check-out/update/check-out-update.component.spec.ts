import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CheckOutFormService } from './check-out-form.service';
import { CheckOutService } from '../service/check-out.service';
import { ICheckOut } from '../check-out.model';

import { CheckOutUpdateComponent } from './check-out-update.component';

describe('CheckOut Management Update Component', () => {
  let comp: CheckOutUpdateComponent;
  let fixture: ComponentFixture<CheckOutUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let checkOutFormService: CheckOutFormService;
  let checkOutService: CheckOutService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CheckOutUpdateComponent],
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
      .overrideTemplate(CheckOutUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckOutUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    checkOutFormService = TestBed.inject(CheckOutFormService);
    checkOutService = TestBed.inject(CheckOutService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const checkOut: ICheckOut = { id: 456 };

      activatedRoute.data = of({ checkOut });
      comp.ngOnInit();

      expect(comp.checkOut).toEqual(checkOut);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckOut>>();
      const checkOut = { id: 123 };
      jest.spyOn(checkOutFormService, 'getCheckOut').mockReturnValue(checkOut);
      jest.spyOn(checkOutService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkOut });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkOut }));
      saveSubject.complete();

      // THEN
      expect(checkOutFormService.getCheckOut).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(checkOutService.update).toHaveBeenCalledWith(expect.objectContaining(checkOut));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckOut>>();
      const checkOut = { id: 123 };
      jest.spyOn(checkOutFormService, 'getCheckOut').mockReturnValue({ id: null });
      jest.spyOn(checkOutService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkOut: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: checkOut }));
      saveSubject.complete();

      // THEN
      expect(checkOutFormService.getCheckOut).toHaveBeenCalled();
      expect(checkOutService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICheckOut>>();
      const checkOut = { id: 123 };
      jest.spyOn(checkOutService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ checkOut });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(checkOutService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
