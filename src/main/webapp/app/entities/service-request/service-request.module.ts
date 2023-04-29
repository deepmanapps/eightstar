import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceRequestComponent } from './list/service-request.component';
import { ServiceRequestDetailComponent } from './detail/service-request-detail.component';
import { ServiceRequestUpdateComponent } from './update/service-request-update.component';
import { ServiceRequestDeleteDialogComponent } from './delete/service-request-delete-dialog.component';
import { ServiceRequestRoutingModule } from './route/service-request-routing.module';

@NgModule({
  imports: [SharedModule, ServiceRequestRoutingModule],
  declarations: [
    ServiceRequestComponent,
    ServiceRequestDetailComponent,
    ServiceRequestUpdateComponent,
    ServiceRequestDeleteDialogComponent,
  ],
})
export class ServiceRequestModule {}
