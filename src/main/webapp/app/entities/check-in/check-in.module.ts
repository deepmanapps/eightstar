import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CheckInComponent } from './list/check-in.component';
import { CheckInDetailComponent } from './detail/check-in-detail.component';
import { CheckInUpdateComponent } from './update/check-in-update.component';
import { CheckInDeleteDialogComponent } from './delete/check-in-delete-dialog.component';
import { CheckInRoutingModule } from './route/check-in-routing.module';

@NgModule({
  imports: [SharedModule, CheckInRoutingModule],
  declarations: [CheckInComponent, CheckInDetailComponent, CheckInUpdateComponent, CheckInDeleteDialogComponent],
})
export class CheckInModule {}
