import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeliveryRequestPlaceDetailComponent } from './delivery-request-place-detail.component';

describe('DeliveryRequestPlace Management Detail Component', () => {
  let comp: DeliveryRequestPlaceDetailComponent;
  let fixture: ComponentFixture<DeliveryRequestPlaceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeliveryRequestPlaceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ deliveryRequestPlace: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DeliveryRequestPlaceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DeliveryRequestPlaceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load deliveryRequestPlace on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.deliveryRequestPlace).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
