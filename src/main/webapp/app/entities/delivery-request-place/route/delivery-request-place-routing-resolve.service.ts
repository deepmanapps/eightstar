import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeliveryRequestPlace } from '../delivery-request-place.model';
import { DeliveryRequestPlaceService } from '../service/delivery-request-place.service';

@Injectable({ providedIn: 'root' })
export class DeliveryRequestPlaceRoutingResolveService implements Resolve<IDeliveryRequestPlace | null> {
  constructor(protected service: DeliveryRequestPlaceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeliveryRequestPlace | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((deliveryRequestPlace: HttpResponse<IDeliveryRequestPlace>) => {
          if (deliveryRequestPlace.body) {
            return of(deliveryRequestPlace.body);
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
