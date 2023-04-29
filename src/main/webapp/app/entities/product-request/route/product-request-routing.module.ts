import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductRequestComponent } from '../list/product-request.component';
import { ProductRequestDetailComponent } from '../detail/product-request-detail.component';
import { ProductRequestUpdateComponent } from '../update/product-request-update.component';
import { ProductRequestRoutingResolveService } from './product-request-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productRequestRoute: Routes = [
  {
    path: '',
    component: ProductRequestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductRequestDetailComponent,
    resolve: {
      productRequest: ProductRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductRequestUpdateComponent,
    resolve: {
      productRequest: ProductRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductRequestUpdateComponent,
    resolve: {
      productRequest: ProductRequestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productRequestRoute)],
  exports: [RouterModule],
})
export class ProductRequestRoutingModule {}
