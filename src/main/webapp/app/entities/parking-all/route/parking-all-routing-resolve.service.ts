import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParkingAll } from '../parking-all.model';
import { ParkingAllService } from '../service/parking-all.service';

@Injectable({ providedIn: 'root' })
export class ParkingAllRoutingResolveService implements Resolve<IParkingAll | null> {
  constructor(protected service: ParkingAllService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParkingAll | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((parkingAll: HttpResponse<IParkingAll>) => {
          if (parkingAll.body) {
            return of(parkingAll.body);
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
