import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';

@Injectable({ providedIn: 'root' })
export class HotelRoutingResolveService implements Resolve<IHotel | null> {
  constructor(protected service: HotelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotel | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hotel: HttpResponse<IHotel>) => {
          if (hotel.body) {
            return of(hotel.body);
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
