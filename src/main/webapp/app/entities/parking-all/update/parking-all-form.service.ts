import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IParkingAll, NewParkingAll } from '../parking-all.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParkingAll for edit and NewParkingAllFormGroupInput for create.
 */
type ParkingAllFormGroupInput = IParkingAll | PartialWithRequiredKeyOf<NewParkingAll>;

type ParkingAllFormDefaults = Pick<NewParkingAll, 'id'>;

type ParkingAllFormGroupContent = {
  id: FormControl<IParkingAll['id'] | NewParkingAll['id']>;
  name: FormControl<IParkingAll['name']>;
  location: FormControl<IParkingAll['location']>;
  hotel: FormControl<IParkingAll['hotel']>;
};

export type ParkingAllFormGroup = FormGroup<ParkingAllFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParkingAllFormService {
  createParkingAllFormGroup(parkingAll: ParkingAllFormGroupInput = { id: null }): ParkingAllFormGroup {
    const parkingAllRawValue = {
      ...this.getFormDefaults(),
      ...parkingAll,
    };
    return new FormGroup<ParkingAllFormGroupContent>({
      id: new FormControl(
        { value: parkingAllRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(parkingAllRawValue.name),
      location: new FormControl(parkingAllRawValue.location),
      hotel: new FormControl(parkingAllRawValue.hotel),
    });
  }

  getParkingAll(form: ParkingAllFormGroup): IParkingAll | NewParkingAll {
    return form.getRawValue() as IParkingAll | NewParkingAll;
  }

  resetForm(form: ParkingAllFormGroup, parkingAll: ParkingAllFormGroupInput): void {
    const parkingAllRawValue = { ...this.getFormDefaults(), ...parkingAll };
    form.reset(
      {
        ...parkingAllRawValue,
        id: { value: parkingAllRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ParkingAllFormDefaults {
    return {
      id: null,
    };
  }
}
