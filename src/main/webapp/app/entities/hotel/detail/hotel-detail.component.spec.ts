import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HotelDetailComponent } from './hotel-detail.component';

describe('Hotel Management Detail Component', () => {
  let comp: HotelDetailComponent;
  let fixture: ComponentFixture<HotelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hotel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HotelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HotelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hotel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hotel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
