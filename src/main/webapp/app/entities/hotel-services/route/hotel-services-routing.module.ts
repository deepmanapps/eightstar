import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HotelServicesComponent } from '../list/hotel-services.component';
import { HotelServicesDetailComponent } from '../detail/hotel-services-detail.component';
import { HotelServicesUpdateComponent } from '../update/hotel-services-update.component';
import { HotelServicesRoutingResolveService } from './hotel-services-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const hotelServicesRoute: Routes = [
  {
    path: '',
    component: HotelServicesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelServicesDetailComponent,
    resolve: {
      hotelServices: HotelServicesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelServicesUpdateComponent,
    resolve: {
      hotelServices: HotelServicesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelServicesUpdateComponent,
    resolve: {
      hotelServices: HotelServicesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hotelServicesRoute)],
  exports: [RouterModule],
})
export class HotelServicesRoutingModule {}
