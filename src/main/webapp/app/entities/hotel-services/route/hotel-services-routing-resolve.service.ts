import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotelServices } from '../hotel-services.model';
import { HotelServicesService } from '../service/hotel-services.service';

@Injectable({ providedIn: 'root' })
export class HotelServicesRoutingResolveService implements Resolve<IHotelServices | null> {
  constructor(protected service: HotelServicesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotelServices | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hotelServices: HttpResponse<IHotelServices>) => {
          if (hotelServices.body) {
            return of(hotelServices.body);
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
