import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceRequestComponent } from '../list/service-request.component';
import { ServiceRequestDetailComponent } from '../detail/service-request-detail.component';
import { ServiceRequestUpdateComponent } from '../update/service-request-update.component';
import { ServiceRequestRoutingResolveService } from './service-request-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const serviceRequestRoute: Routes = [
  {
    path: '',
    component: ServiceRequestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceRequestDetailComponent,
    resolve: {
      serviceRequest: ServiceRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceRequestUpdateComponent,
    resolve: {
      serviceRequest: ServiceRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceRequestUpdateComponent,
    resolve: {
      serviceRequest: ServiceRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serviceRequestRoute)],
  exports: [RouterModule],
})
export class ServiceRequestRoutingModule {}
