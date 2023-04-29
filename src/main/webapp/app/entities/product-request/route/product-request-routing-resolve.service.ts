import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductRequest } from '../product-request.model';
import { ProductRequestService } from '../service/product-request.service';

@Injectable({ providedIn: 'root' })
export class ProductRequestRoutingResolveService implements Resolve<IProductRequest | null> {
  constructor(protected service: ProductRequestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductRequest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productRequest: HttpResponse<IProductRequest>) => {
          if (productRequest.body) {
            return of(productRequest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
