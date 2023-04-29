import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParkingAllService } from '../service/parking-all.service';

import { ParkingAllComponent } from './parking-all.component';

describe('ParkingAll Management Component', () => {
  let comp: ParkingAllComponent;
  let fixture: ComponentFixture<ParkingAllComponent>;
  let service: ParkingAllService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'parking-all', component: ParkingAllComponent }]), HttpClientTestingModule],
      declarations: [ParkingAllComponent],
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
      .overrideTemplate(ParkingAllComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParkingAllComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ParkingAllService);

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
    expect(comp.parkingAlls?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to parkingAllService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getParkingAllIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getParkingAllIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});