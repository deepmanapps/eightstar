import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServiceRequest } from '../service-request.model';
import { ServiceRequestService } from '../service/service-request.service';

@Injectable({ providedIn: 'root' })
export class ServiceRequestRoutingResolveService implements Resolve<IServiceRequest | null> {
  constructor(protected service: ServiceRequestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceRequest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((serviceRequest: HttpResponse<IServiceRequest>) => {
          if (serviceRequest.body) {
            return of(serviceRequest.body);
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
