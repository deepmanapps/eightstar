import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParkingAll } from '../parking-all.model';
import { ParkingAllService } from '../service/parking-all.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './parking-all-delete-dialog.component.html',
})
export class ParkingAllDeleteDialogComponent {
  parkingAll?: IParkingAll;

  constructor(protected parkingAllService: ParkingAllService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parkingAllService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
