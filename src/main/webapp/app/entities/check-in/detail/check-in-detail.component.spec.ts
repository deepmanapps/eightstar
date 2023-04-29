import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckInDetailComponent } from './check-in-detail.component';

describe('CheckIn Management Detail Component', () => {
  let comp: CheckInDetailComponent;
  let fixture: ComponentFixture<CheckInDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckInDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ checkIn: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CheckInDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CheckInDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load checkIn on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.checkIn).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
