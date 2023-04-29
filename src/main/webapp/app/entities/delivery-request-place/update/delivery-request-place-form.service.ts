import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDeliveryRequestPlace, NewDeliveryRequestPlace } from '../delivery-request-place.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDeliveryRequestPlace for edit and NewDeliveryRequestPlaceFormGroupInput for create.
 */
type DeliveryRequestPlaceFormGroupInput = IDeliveryRequestPlace | PartialWithRequiredKeyOf<NewDeliveryRequestPlace>;

type DeliveryRequestPlaceFormDefaults = Pick<NewDeliveryRequestPlace, 'id'>;

type DeliveryRequestPlaceFormGroupContent = {
  id: FormControl<IDeliveryRequestPlace['id'] | NewDeliveryRequestPlace['id']>;
  name: FormControl<IDeliveryRequestPlace['name']>;
  hotel: FormControl<IDeliveryRequestPlace['hotel']>;
};

export type DeliveryRequestPlaceFormGroup = FormGroup<DeliveryRequestPlaceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DeliveryRequestPlaceFormService {
  createDeliveryRequestPlaceFormGroup(
    deliveryRequestPlace: DeliveryRequestPlaceFormGroupInput = { id: null }
  ): DeliveryRequestPlaceFormGroup {
    const deliveryRequestPlaceRawValue = {
      ...this.getFormDefaults(),
      ...deliveryRequestPlace,
    };
    return new FormGroup<DeliveryRequestPlaceFormGroupContent>({
      id: new FormControl(
        { value: deliveryRequestPlaceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(deliveryRequestPlaceRawValue.name),
      hotel: new FormControl(deliveryRequestPlaceRawValue.hotel),
    });
  }

  getDeliveryRequestPlace(form: DeliveryRequestPlaceFormGroup): IDeliveryRequestPlace | NewDeliveryRequestPlace {
    return form.getRawValue() as IDeliveryRequestPlace | NewDeliveryRequestPlace;
  }

  resetForm(form: DeliveryRequestPlaceFormGroup, deliveryRequestPlace: DeliveryRequestPlaceFormGroupInput): void {
    const deliveryRequestPlaceRawValue = { ...this.getFormDefaults(), ...deliveryRequestPlace };
    form.reset(
      {
        ...deliveryRequestPlaceRawValue,
        id: { value: deliveryRequestPlaceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DeliveryRequestPlaceFormDefaults {
    return {
      id: null,
    };
  }
}
