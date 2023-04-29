import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { HotelServicesService } from '../service/hotel-services.service';

import { HotelServicesComponent } from './hotel-services.component';

describe('HotelServices Management Component', () => {
  let comp: HotelServicesComponent;
  let fixture: ComponentFixture<HotelServicesComponent>;
  let service: HotelServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'hotel-services', component: HotelServicesComponent }]), HttpClientTestingModule],
      declarations: [HotelServicesComponent],
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
      .overrideTemplate(HotelServicesComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelServicesComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HotelServicesService);

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
    expect(comp.hotelServices?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to hotelServicesService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getHotelServicesIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getHotelServicesIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
