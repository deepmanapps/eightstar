import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ServiceRequestService } from '../service/service-request.service';

import { ServiceRequestComponent } from './service-request.component';

describe('ServiceRequest Management Component', () => {
  let comp: ServiceRequestComponent;
  let fixture: ComponentFixture<ServiceRequestComponent>;
  let service: ServiceRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'service-request', component: ServiceRequestComponent }]), HttpClientTestingModule],
      declarations: [ServiceRequestComponent],
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
      .overrideTemplate(ServiceRequestComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ServiceRequestService);

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
    expect(comp.serviceRequests?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to serviceRequestService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getServiceRequestIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getServiceRequestIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
