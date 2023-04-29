import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductRequestDetailComponent } from './product-request-detail.component';

describe('ProductRequest Management Detail Component', () => {
  let comp: ProductRequestDetailComponent;
  let fixture: ComponentFixture<ProductRequestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductRequestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productRequest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProductRequestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductRequestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productRequest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productRequest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
