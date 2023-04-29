import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductRequest, NewProductRequest } from '../product-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductRequest for edit and NewProductRequestFormGroupInput for create.
 */
type ProductRequestFormGroupInput = IProductRequest | PartialWithRequiredKeyOf<NewProductRequest>;

type ProductRequestFormDefaults = Pick<NewProductRequest, 'id'>;

type ProductRequestFormGroupContent = {
  id: FormControl<IProductRequest['id'] | NewProductRequest['id']>;
  productName: FormControl<IProductRequest['productName']>;
  productUnitPrice: FormControl<IProductRequest['productUnitPrice']>;
  productTotalPrice: FormControl<IProductRequest['productTotalPrice']>;
  requestedQuantity: FormControl<IProductRequest['requestedQuantity']>;
  serviceRequest: FormControl<IProductRequest['serviceRequest']>;
};

export type ProductRequestFormGroup = FormGroup<ProductRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductRequestFormService {
  createProductRequestFormGroup(productRequest: ProductRequestFormGroupInput = { id: null }): ProductRequestFormGroup {
    const productRequestRawValue = {
      ...this.getFormDefaults(),
      ...productRequest,
    };
    return new FormGroup<ProductRequestFormGroupContent>({
      id: new FormControl(
        { value: productRequestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productName: new FormControl(productRequestRawValue.productName),
      productUnitPrice: new FormControl(productRequestRawValue.productUnitPrice),
      productTotalPrice: new FormControl(productRequestRawValue.productTotalPrice),
      requestedQuantity: new FormControl(productRequestRawValue.requestedQuantity),
      serviceRequest: new FormControl(productRequestRawValue.serviceRequest),
    });
  }

  getProductRequest(form: ProductRequestFormGroup): IProductRequest | NewProductRequest {
    return form.getRawValue() as IProductRequest | NewProductRequest;
  }

  resetForm(form: ProductRequestFormGroup, productRequest: ProductRequestFormGroupInput): void {
    const productRequestRawValue = { ...this.getFormDefaults(), ...productRequest };
    form.reset(
      {
        ...productRequestRawValue,
        id: { value: productRequestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductRequestFormDefaults {
    return {
      id: null,
    };
  }
}
