import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductRequestComponent } from './list/product-request.component';
import { ProductRequestDetailComponent } from './detail/product-request-detail.component';
import { ProductRequestUpdateComponent } from './update/product-request-update.component';
import { ProductRequestDeleteDialogComponent } from './delete/product-request-delete-dialog.component';
import { ProductRequestRoutingModule } from './route/product-request-routing.module';

@NgModule({
  imports: [SharedModule, ProductRequestRoutingModule],
  declarations: [
    ProductRequestComponent,
    ProductRequestDetailComponent,
    ProductRequestUpdateComponent,
    ProductRequestDeleteDialogComponent,
  ],
})
export class ProductRequestModule {}
