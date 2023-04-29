import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CheckOutFormService, CheckOutFormGroup } from './check-out-form.service';
import { ICheckOut } from '../check-out.model';
import { CheckOutService } from '../service/check-out.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'es-check-out-update',
  templateUrl: './check-out-update.component.html',
})
export class CheckOutUpdateComponent implements OnInit {
  isSaving = false;
  checkOut: ICheckOut | null = null;

  editForm: CheckOutFormGroup = this.checkOutFormService.createCheckOutFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected checkOutService: CheckOutService,
    protected checkOutFormService: CheckOutFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkOut }) => {
      this.checkOut = checkOut;
      if (checkOut) {
        this.updateForm(checkOut);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('eightStarApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkOut = this.checkOutFormService.getCheckOut(this.editForm);
    if (checkOut.id !== null) {
      this.subscribeToSaveResponse(this.checkOutService.update(checkOut));
    } else {
      this.subscribeToSaveResponse(this.checkOutService.create(checkOut));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckOut>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(checkOut: ICheckOut): void {
    this.checkOut = checkOut;
    this.checkOutFormService.resetForm(this.editForm, checkOut);
  }
}
