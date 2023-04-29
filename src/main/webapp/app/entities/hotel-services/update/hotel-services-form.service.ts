import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHotelServices, NewHotelServices } from '../hotel-services.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHotelServices for edit and NewHotelServicesFormGroupInput for create.
 */
type HotelServicesFormGroupInput = IHotelServices | PartialWithRequiredKeyOf<NewHotelServices>;

type HotelServicesFormDefaults = Pick<NewHotelServices, 'id' | 'active' | 'forGuest'>;

type HotelServicesFormGroupContent = {
  id: FormControl<IHotelServices['id'] | NewHotelServices['id']>;
  active: FormControl<IHotelServices['active']>;
  forGuest: FormControl<IHotelServices['forGuest']>;
  servicePrice: FormControl<IHotelServices['servicePrice']>;
  hotel: FormControl<IHotelServices['hotel']>;
  services: FormControl<IHotelServices['services']>;
};

export type HotelServicesFormGroup = FormGroup<HotelServicesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HotelServicesFormService {
  createHotelServicesFormGroup(hotelServices: HotelServicesFormGroupInput = { id: null }): HotelServicesFormGroup {
    const hotelServicesRawValue = {
      ...this.getFormDefaults(),
      ...hotelServices,
    };
    return new FormGroup<HotelServicesFormGroupContent>({
      id: new FormControl(
        { value: hotelServicesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      active: new FormControl(hotelServicesRawValue.active),
      forGuest: new FormControl(hotelServicesRawValue.forGuest),
      servicePrice: new FormControl(hotelServicesRawValue.servicePrice),
      hotel: new FormControl(hotelServicesRawValue.hotel),
      services: new FormControl(hotelServicesRawValue.services),
    });
  }

  getHotelServices(form: HotelServicesFormGroup): IHotelServices | NewHotelServices {
    return form.getRawValue() as IHotelServices | NewHotelServices;
  }

  resetForm(form: HotelServicesFormGroup, hotelServices: HotelServicesFormGroupInput): void {
    const hotelServicesRawValue = { ...this.getFormDefaults(), ...hotelServices };
    form.reset(
      {
        ...hotelServicesRawValue,
        id: { value: hotelServicesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HotelServicesFormDefaults {
    return {
      id: null,
      active: false,
      forGuest: false,
    };
  }
}
