import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HotelServicesComponent } from './list/hotel-services.component';
import { HotelServicesDetailComponent } from './detail/hotel-services-detail.component';
import { HotelServicesUpdateComponent } from './update/hotel-services-update.component';
import { HotelServicesDeleteDialogComponent } from './delete/hotel-services-delete-dialog.component';
import { HotelServicesRoutingModule } from './route/hotel-services-routing.module';

@NgModule({
  imports: [SharedModule, HotelServicesRoutingModule],
  declarations: [HotelServicesComponent, HotelServicesDetailComponent, HotelServicesUpdateComponent, HotelServicesDeleteDialogComponent],
})
export class HotelServicesModule {}
