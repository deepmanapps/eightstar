import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CheckInComponent } from '../list/check-in.component';
import { CheckInDetailComponent } from '../detail/check-in-detail.component';
import { CheckInUpdateComponent } from '../update/check-in-update.component';
import { CheckInRoutingResolveService } from './check-in-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const checkInRoute: Routes = [
  {
    path: '',
    component: CheckInComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CheckInDetailComponent,
    resolve: {
      checkIn: CheckInRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CheckInUpdateComponent,
    resolve: {
      checkIn: CheckInRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CheckInUpdateComponent,
    resolve: {
      checkIn: CheckInRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(checkInRoute)],
  exports: [RouterModule],
})
export class CheckInRoutingModule {}
