import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHotel, NewHotel } from '../hotel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHotel for edit and NewHotelFormGroupInput for create.
 */
type HotelFormGroupInput = IHotel | PartialWithRequiredKeyOf<NewHotel>;

type HotelFormDefaults = Pick<NewHotel, 'id'>;

type HotelFormGroupContent = {
  id: FormControl<IHotel['id'] | NewHotel['id']>;
  hotelId: FormControl<IHotel['hotelId']>;
  name: FormControl<IHotel['name']>;
  description: FormControl<IHotel['description']>;
  adresse: FormControl<IHotel['adresse']>;
  starsNumber: FormControl<IHotel['starsNumber']>;
  emergencyNumber: FormControl<IHotel['emergencyNumber']>;
  location: FormControl<IHotel['location']>;
};

export type HotelFormGroup = FormGroup<HotelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HotelFormService {
  createHotelFormGroup(hotel: HotelFormGroupInput = { id: null }): HotelFormGroup {
    const hotelRawValue = {
      ...this.getFormDefaults(),
      ...hotel,
    };
    return new FormGroup<HotelFormGroupContent>({
      id: new FormControl(
        { value: hotelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      hotelId: new FormControl(hotelRawValue.hotelId),
      name: new FormControl(hotelRawValue.name),
      description: new FormControl(hotelRawValue.description),
      adresse: new FormControl(hotelRawValue.adresse),
      starsNumber: new FormControl(hotelRawValue.starsNumber),
      emergencyNumber: new FormControl(hotelRawValue.emergencyNumber),
      location: new FormControl(hotelRawValue.location),
    });
  }

  getHotel(form: HotelFormGroup): IHotel | NewHotel {
    return form.getRawValue() as IHotel | NewHotel;
  }

  resetForm(form: HotelFormGroup, hotel: HotelFormGroupInput): void {
    const hotelRawValue = { ...this.getFormDefaults(), ...hotel };
    form.reset(
      {
        ...hotelRawValue,
        id: { value: hotelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HotelFormDefaults {
    return {
      id: null,
    };
  }
}
