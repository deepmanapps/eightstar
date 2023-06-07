import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServicesComponent } from './list/services.component';
import { DropdownMenu1Component } from './list/dropdown-menu1.component';
import { ServicesDetailComponent } from './detail/services-detail.component';
import { ServicesUpdateComponent } from './update/services-update.component';
import { ServicesDeleteDialogComponent } from './delete/services-delete-dialog.component';
import { ServicesRoutingModule } from './route/services-routing.module';

@NgModule({
  imports: [SharedModule, ServicesRoutingModule],
  declarations: [
    DropdownMenu1Component,
    ServicesComponent,
    ServicesDetailComponent,
    ServicesUpdateComponent,
    ServicesDeleteDialogComponent,
  ],
})
export class ServicesModule {}
