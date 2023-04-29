import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelServices } from '../hotel-services.model';
import { HotelServicesService } from '../service/hotel-services.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './hotel-services-delete-dialog.component.html',
})
export class HotelServicesDeleteDialogComponent {
  hotelServices?: IHotelServices;

  constructor(protected hotelServicesService: HotelServicesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelServicesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
