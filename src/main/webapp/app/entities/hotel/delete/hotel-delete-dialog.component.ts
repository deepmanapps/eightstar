import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './hotel-delete-dialog.component.html',
})
export class HotelDeleteDialogComponent {
  hotel?: IHotel;

  constructor(protected hotelService: HotelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
