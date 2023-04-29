import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DeliveryRequestPlaceComponent } from './list/delivery-request-place.component';
import { DeliveryRequestPlaceDetailComponent } from './detail/delivery-request-place-detail.component';
import { DeliveryRequestPlaceUpdateComponent } from './update/delivery-request-place-update.component';
import { DeliveryRequestPlaceDeleteDialogComponent } from './delete/delivery-request-place-delete-dialog.component';
import { DeliveryRequestPlaceRoutingModule } from './route/delivery-request-place-routing.module';

@NgModule({
  imports: [SharedModule, DeliveryRequestPlaceRoutingModule],
  declarations: [
    DeliveryRequestPlaceComponent,
    DeliveryRequestPlaceDetailComponent,
    DeliveryRequestPlaceUpdateComponent,
    DeliveryRequestPlaceDeleteDialogComponent,
  ],
})
export class DeliveryRequestPlaceModule {}
