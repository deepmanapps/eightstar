import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DeliveryRequestPlaceService } from '../service/delivery-request-place.service';

import { DeliveryRequestPlaceComponent } from './delivery-request-place.component';

describe('DeliveryRequestPlace Management Component', () => {
  let comp: DeliveryRequestPlaceComponent;
  let fixture: ComponentFixture<DeliveryRequestPlaceComponent>;
  let service: DeliveryRequestPlaceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'delivery-request-place', component: DeliveryRequestPlaceComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [DeliveryRequestPlaceComponent],
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
      .overrideTemplate(DeliveryRequestPlaceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DeliveryRequestPlaceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DeliveryRequestPlaceService);

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
    expect(comp.deliveryRequestPlaces?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to deliveryRequestPlaceService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDeliveryRequestPlaceIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDeliveryRequestPlaceIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
