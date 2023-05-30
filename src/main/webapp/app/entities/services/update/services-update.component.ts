import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ServicesFormService, ServicesFormGroup } from './services-form.service';
import { IServices } from '../services.model';
import { ServicesService } from '../service/services.service';

@Component({
  selector: 'es-services-update',
  templateUrl: './services-update.component.html',
})
export class ServicesUpdateComponent implements OnInit {
  isSaving = false;
  services: IServices | null = null;

  servicesSharedCollection: IServices[] = [];

  editForm: ServicesFormGroup = this.servicesFormService.createServicesFormGroup();

  constructor(
    protected servicesService: ServicesService,
    protected servicesFormService: ServicesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareServices = (o1: IServices | null, o2: IServices | null): boolean => this.servicesService.compareServices(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ services }) => {
      this.services = services;
      if (services) {
        this.updateForm(services);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const services = this.servicesFormService.getServices(this.editForm);
    if (services.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.servicesService.update(services));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.servicesService.create(services));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServices>>): void {
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

  protected updateForm(services: IServices): void {
    this.services = services;
    this.servicesFormService.resetForm(this.editForm, services);

    this.servicesSharedCollection = this.servicesService.addServicesToCollectionIfMissing<IServices>(
      this.servicesSharedCollection,
      services.parentService
    );
  }

  protected loadRelationshipsOptions(): void {
    this.servicesService
      .query()
      .pipe(map((res: HttpResponse<IServices[]>) => res.body ?? []))
      .pipe(
        map((services: IServices[]) =>
          this.servicesService.addServicesToCollectionIfMissing<IServices>(services, this.services?.parentService)
        )
      )
      .subscribe((services: IServices[]) => (this.servicesSharedCollection = services));
  }
}
