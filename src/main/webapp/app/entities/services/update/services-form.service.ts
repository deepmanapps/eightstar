import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServices, NewServices } from '../services.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServices for edit and NewServicesFormGroupInput for create.
 */
type ServicesFormGroupInput = IServices | PartialWithRequiredKeyOf<NewServices>;

type ServicesFormDefaults = Pick<NewServices, 'id'>;

type ServicesFormGroupContent = {
  id: FormControl<IServices['id'] | NewServices['id']>;
  nom: FormControl<IServices['nom']>;
  description: FormControl<IServices['description']>;
  parentService: FormControl<IServices['parentService']>;
};

export type ServicesFormGroup = FormGroup<ServicesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicesFormService {
  createServicesFormGroup(services: ServicesFormGroupInput = { id: null }): ServicesFormGroup {
    const servicesRawValue = {
      ...this.getFormDefaults(),
      ...services,
    };
    return new FormGroup<ServicesFormGroupContent>({
      id: new FormControl(
        { value: servicesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(servicesRawValue.nom),
      description: new FormControl(servicesRawValue.description),
      parentService: new FormControl(servicesRawValue.parentService),
    });
  }

  getServices(form: ServicesFormGroup): IServices | NewServices {
    return form.getRawValue() as IServices | NewServices;
  }

  resetForm(form: ServicesFormGroup, services: ServicesFormGroupInput): void {
    const servicesRawValue = { ...this.getFormDefaults(), ...services };
    form.reset(
      {
        ...servicesRawValue,
        id: { value: servicesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ServicesFormDefaults {
    return {
      id: null,
    };
  }
}
