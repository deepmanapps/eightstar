import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICheckOut } from '../check-out.model';
import { CheckOutService } from '../service/check-out.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './check-out-delete-dialog.component.html',
})
export class CheckOutDeleteDialogComponent {
  checkOut?: ICheckOut;

  constructor(protected checkOutService: CheckOutService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkOutService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
