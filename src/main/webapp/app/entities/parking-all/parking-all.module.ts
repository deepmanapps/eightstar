import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParkingAllComponent } from './list/parking-all.component';
import { ParkingAllDetailComponent } from './detail/parking-all-detail.component';
import { ParkingAllUpdateComponent } from './update/parking-all-update.component';
import { ParkingAllDeleteDialogComponent } from './delete/parking-all-delete-dialog.component';
import { ParkingAllRoutingModule } from './route/parking-all-routing.module';

@NgModule({
  imports: [SharedModule, ParkingAllRoutingModule],
  declarations: [ParkingAllComponent, ParkingAllDetailComponent, ParkingAllUpdateComponent, ParkingAllDeleteDialogComponent],
})
export class ParkingAllModule {}
