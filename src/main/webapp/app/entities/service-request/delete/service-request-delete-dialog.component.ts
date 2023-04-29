import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceRequest } from '../service-request.model';
import { ServiceRequestService } from '../service/service-request.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './service-request-delete-dialog.component.html',
})
export class ServiceRequestDeleteDialogComponent {
  serviceRequest?: IServiceRequest;

  constructor(protected serviceRequestService: ServiceRequestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceRequestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
