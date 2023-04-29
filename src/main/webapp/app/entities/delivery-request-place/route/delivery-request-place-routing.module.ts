import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DeliveryRequestPlaceComponent } from '../list/delivery-request-place.component';
import { DeliveryRequestPlaceDetailComponent } from '../detail/delivery-request-place-detail.component';
import { DeliveryRequestPlaceUpdateComponent } from '../update/delivery-request-place-update.component';
import { DeliveryRequestPlaceRoutingResolveService } from './delivery-request-place-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const deliveryRequestPlaceRoute: Routes = [
  {
    path: '',
    component: DeliveryRequestPlaceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DeliveryRequestPlaceDetailComponent,
    resolve: {
      deliveryRequestPlace: DeliveryRequestPlaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DeliveryRequestPlaceUpdateComponent,
    resolve: {
      deliveryRequestPlace: DeliveryRequestPlaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DeliveryRequestPlaceUpdateComponent,
    resolve: {
      deliveryRequestPlace: DeliveryRequestPlaceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(deliveryRequestPlaceRoute)],
  exports: [RouterModule],
})
export class DeliveryRequestPlaceRoutingModule {}
