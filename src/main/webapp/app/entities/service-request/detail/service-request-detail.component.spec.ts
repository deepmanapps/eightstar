import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiceRequestDetailComponent } from './service-request-detail.component';

describe('ServiceRequest Management Detail Component', () => {
  let comp: ServiceRequestDetailComponent;
  let fixture: ComponentFixture<ServiceRequestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServiceRequestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ serviceRequest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ServiceRequestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ServiceRequestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load serviceRequest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.serviceRequest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
