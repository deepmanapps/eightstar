import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CheckInService } from '../service/check-in.service';

import { CheckInComponent } from './check-in.component';

describe('CheckIn Management Component', () => {
  let comp: CheckInComponent;
  let fixture: ComponentFixture<CheckInComponent>;
  let service: CheckInService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'check-in', component: CheckInComponent }]), HttpClientTestingModule],
      declarations: [CheckInComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(CheckInComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CheckInComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CheckInService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.checkIns?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to checkInService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getCheckInIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCheckInIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
