import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductRequest } from '../product-request.model';

@Component({
  selector: 'es-product-request-detail',
  templateUrl: './product-request-detail.component.html',
})
export class ProductRequestDetailComponent implements OnInit {
  productRequest: IProductRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productRequest }) => {
      this.productRequest = productRequest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
