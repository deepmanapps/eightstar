import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HotelServicesDetailComponent } from './hotel-services-detail.component';

describe('HotelServices Management Detail Component', () => {
  let comp: HotelServicesDetailComponent;
  let fixture: ComponentFixture<HotelServicesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelServicesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hotelServices: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HotelServicesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HotelServicesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hotelServices on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hotelServices).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
