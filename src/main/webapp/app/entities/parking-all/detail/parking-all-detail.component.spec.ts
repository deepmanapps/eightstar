import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParkingAllDetailComponent } from './parking-all-detail.component';

describe('ParkingAll Management Detail Component', () => {
  let comp: ParkingAllDetailComponent;
  let fixture: ComponentFixture<ParkingAllDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParkingAllDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ parkingAll: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ParkingAllDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ParkingAllDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load parkingAll on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.parkingAll).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
