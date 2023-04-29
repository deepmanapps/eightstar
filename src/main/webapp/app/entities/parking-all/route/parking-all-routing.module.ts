import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParkingAllComponent } from '../list/parking-all.component';
import { ParkingAllDetailComponent } from '../detail/parking-all-detail.component';
import { ParkingAllUpdateComponent } from '../update/parking-all-update.component';
import { ParkingAllRoutingResolveService } from './parking-all-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const parkingAllRoute: Routes = [
  {
    path: '',
    component: ParkingAllComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParkingAllDetailComponent,
    resolve: {
      parkingAll: ParkingAllRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParkingAllUpdateComponent,
    resolve: {
      parkingAll: ParkingAllRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParkingAllUpdateComponent,
    resolve: {
      parkingAll: ParkingAllRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(parkingAllRoute)],
  exports: [RouterModule],
})
export class ParkingAllRoutingModule {}
