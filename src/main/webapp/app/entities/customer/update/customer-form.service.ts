import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICustomer, NewCustomer } from '../customer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICustomer for edit and NewCustomerFormGroupInput for create.
 */
type CustomerFormGroupInput = ICustomer | PartialWithRequiredKeyOf<NewCustomer>;

type CustomerFormDefaults = Pick<NewCustomer, 'id'>;

type CustomerFormGroupContent = {
  id: FormControl<ICustomer['id'] | NewCustomer['id']>;
  customerId: FormControl<ICustomer['customerId']>;
  firstName: FormControl<ICustomer['firstName']>;
  lastName: FormControl<ICustomer['lastName']>;
  email: FormControl<ICustomer['email']>;
  phoneNumber: FormControl<ICustomer['phoneNumber']>;
  streetAdress: FormControl<ICustomer['streetAdress']>;
  line1: FormControl<ICustomer['line1']>;
  line2: FormControl<ICustomer['line2']>;
  city: FormControl<ICustomer['city']>;
  state: FormControl<ICustomer['state']>;
  zipCode: FormControl<ICustomer['zipCode']>;
};

export type CustomerFormGroup = FormGroup<CustomerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CustomerFormService {
  createCustomerFormGroup(customer: CustomerFormGroupInput = { id: null }): CustomerFormGroup {
    const customerRawValue = {
      ...this.getFormDefaults(),
      ...customer,
    };
    return new FormGroup<CustomerFormGroupContent>({
      id: new FormControl(
        { value: customerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      customerId: new FormControl(customerRawValue.customerId, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(customerRawValue.firstName),
      lastName: new FormControl(customerRawValue.lastName),
      email: new FormControl(customerRawValue.email),
      phoneNumber: new FormControl(customerRawValue.phoneNumber),
      streetAdress: new FormControl(customerRawValue.streetAdress),
      line1: new FormControl(customerRawValue.line1),
      line2: new FormControl(customerRawValue.line2),
      city: new FormControl(customerRawValue.city),
      state: new FormControl(customerRawValue.state),
      zipCode: new FormControl(customerRawValue.zipCode),
    });
  }

  getCustomer(form: CustomerFormGroup): ICustomer | NewCustomer {
    return form.getRawValue() as ICustomer | NewCustomer;
  }

  resetForm(form: CustomerFormGroup, customer: CustomerFormGroupInput): void {
    const customerRawValue = { ...this.getFormDefaults(), ...customer };
    form.reset(
      {
        ...customerRawValue,
        id: { value: customerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CustomerFormDefaults {
    return {
      id: null,
    };
  }
}
