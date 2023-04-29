import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeliveryRequestPlace } from '../delivery-request-place.model';
import { DeliveryRequestPlaceService } from '../service/delivery-request-place.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './delivery-request-place-delete-dialog.component.html',
})
export class DeliveryRequestPlaceDeleteDialogComponent {
  deliveryRequestPlace?: IDeliveryRequestPlace;

  constructor(protected deliveryRequestPlaceService: DeliveryRequestPlaceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deliveryRequestPlaceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
