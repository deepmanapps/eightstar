import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProductRequestFormService, ProductRequestFormGroup } from './product-request-form.service';
import { IProductRequest } from '../product-request.model';
import { ProductRequestService } from '../service/product-request.service';
import { IServiceRequest } from 'app/entities/service-request/service-request.model';
import { ServiceRequestService } from 'app/entities/service-request/service/service-request.service';

@Component({
  selector: 'es-product-request-update',
  templateUrl: './product-request-update.component.html',
})
export class ProductRequestUpdateComponent implements OnInit {
  isSaving = false;
  productRequest: IProductRequest | null = null;

  serviceRequestsSharedCollection: IServiceRequest[] = [];

  editForm: ProductRequestFormGroup = this.productRequestFormService.createProductRequestFormGroup();

  constructor(
    protected productRequestService: ProductRequestService,
    protected productRequestFormService: ProductRequestFormService,
    protected serviceRequestService: ServiceRequestService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareServiceRequest = (o1: IServiceRequest | null, o2: IServiceRequest | null): boolean =>
    this.serviceRequestService.compareServiceRequest(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productRequest }) => {
      this.productRequest = productRequest;
      if (productRequest) {
        this.updateForm(productRequest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productRequest = this.productRequestFormService.getProductRequest(this.editForm);
    if (productRequest.id !== null) {
      // @ts-ignore
      this.subscribeToSaveResponse(this.productRequestService.update(productRequest));
    } else {
      // @ts-ignore
      this.subscribeToSaveResponse(this.productRequestService.create(productRequest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductRequest>>): void {
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

  protected updateForm(productRequest: IProductRequest): void {
    this.productRequest = productRequest;
    this.productRequestFormService.resetForm(this.editForm, productRequest);

    this.serviceRequestsSharedCollection = this.serviceRequestService.addServiceRequestToCollectionIfMissing<IServiceRequest>(
      this.serviceRequestsSharedCollection,
      productRequest.serviceRequest
    );
  }

  protected loadRelationshipsOptions(): void {
    this.serviceRequestService
      .query()
      .pipe(map((res: HttpResponse<IServiceRequest[]>) => res.body ?? []))
      .pipe(
        map((serviceRequests: IServiceRequest[]) =>
          this.serviceRequestService.addServiceRequestToCollectionIfMissing<IServiceRequest>(
            serviceRequests,
            this.productRequest?.serviceRequest
          )
        )
      )
      .subscribe((serviceRequests: IServiceRequest[]) => (this.serviceRequestsSharedCollection = serviceRequests));
  }
}
