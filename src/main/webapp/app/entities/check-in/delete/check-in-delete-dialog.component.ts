import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckIn } from '../check-in.model';
import { CheckInService } from '../service/check-in.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './check-in-delete-dialog.component.html',
})
export class CheckInDeleteDialogComponent {
  checkIn?: ICheckIn;

  constructor(protected checkInService: CheckInService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkInService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
