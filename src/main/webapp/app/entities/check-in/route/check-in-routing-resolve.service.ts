import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICheckIn } from '../check-in.model';
import { CheckInService } from '../service/check-in.service';

@Injectable({ providedIn: 'root' })
export class CheckInRoutingResolveService implements Resolve<ICheckIn | null> {
  constructor(protected service: CheckInService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckIn | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((checkIn: HttpResponse<ICheckIn>) => {
          if (checkIn.body) {
            return of(checkIn.body);
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
