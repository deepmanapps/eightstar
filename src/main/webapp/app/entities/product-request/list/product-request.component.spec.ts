import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProductRequestService } from '../service/product-request.service';

import { ProductRequestComponent } from './product-request.component';

describe('ProductRequest Management Component', () => {
  let comp: ProductRequestComponent;
  let fixture: ComponentFixture<ProductRequestComponent>;
  let service: ProductRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'product-request', component: ProductRequestComponent }]), HttpClientTestingModule],
      declarations: [ProductRequestComponent],
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
      .overrideTemplate(ProductRequestComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductRequestComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProductRequestService);

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
    expect(comp.productRequests?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to productRequestService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProductRequestIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProductRequestIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
