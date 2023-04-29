import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckIn, NewCheckIn } from '../check-in.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICheckIn for edit and NewCheckInFormGroupInput for create.
 */
type CheckInFormGroupInput = ICheckIn | PartialWithRequiredKeyOf<NewCheckIn>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICheckIn | NewCheckIn> = Omit<T, 'arrivalDate' | 'departureDate'> & {
  arrivalDate?: string | null;
  departureDate?: string | null;
};

type CheckInFormRawValue = FormValueOf<ICheckIn>;

type NewCheckInFormRawValue = FormValueOf<NewCheckIn>;

type CheckInFormDefaults = Pick<NewCheckIn, 'id' | 'arrivalDate' | 'departureDate' | 'smooking'>;

type CheckInFormGroupContent = {
  id: FormControl<CheckInFormRawValue['id'] | NewCheckIn['id']>;
  identityPath: FormControl<CheckInFormRawValue['identityPath']>;
  status: FormControl<CheckInFormRawValue['status']>;
  depositAmount: FormControl<CheckInFormRawValue['depositAmount']>;
  paymentMethod: FormControl<CheckInFormRawValue['paymentMethod']>;
  arrivalDate: FormControl<CheckInFormRawValue['arrivalDate']>;
  departureDate: FormControl<CheckInFormRawValue['departureDate']>;
  roomType: FormControl<CheckInFormRawValue['roomType']>;
  smooking: FormControl<CheckInFormRawValue['smooking']>;
  adults: FormControl<CheckInFormRawValue['adults']>;
  children: FormControl<CheckInFormRawValue['children']>;
  notes: FormControl<CheckInFormRawValue['notes']>;
  checkOut: FormControl<CheckInFormRawValue['checkOut']>;
  hotel: FormControl<CheckInFormRawValue['hotel']>;
  customer: FormControl<CheckInFormRawValue['customer']>;
};

export type CheckInFormGroup = FormGroup<CheckInFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CheckInFormService {
  createCheckInFormGroup(checkIn: CheckInFormGroupInput = { id: null }): CheckInFormGroup {
    const checkInRawValue = this.convertCheckInToCheckInRawValue({
      ...this.getFormDefaults(),
      ...checkIn,
    });
    return new FormGroup<CheckInFormGroupContent>({
      id: new FormControl(
        { value: checkInRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      identityPath: new FormControl(checkInRawValue.identityPath),
      status: new FormControl(checkInRawValue.status),
      depositAmount: new FormControl(checkInRawValue.depositAmount),
      paymentMethod: new FormControl(checkInRawValue.paymentMethod),
      arrivalDate: new FormControl(checkInRawValue.arrivalDate),
      departureDate: new FormControl(checkInRawValue.departureDate),
      roomType: new FormControl(checkInRawValue.roomType),
      smooking: new FormControl(checkInRawValue.smooking),
      adults: new FormControl(checkInRawValue.adults),
      children: new FormControl(checkInRawValue.children),
      notes: new FormControl(checkInRawValue.notes),
      checkOut: new FormControl(checkInRawValue.checkOut),
      hotel: new FormControl(checkInRawValue.hotel),
      customer: new FormControl(checkInRawValue.customer),
    });
  }

  getCheckIn(form: CheckInFormGroup): ICheckIn | NewCheckIn {
    return this.convertCheckInRawValueToCheckIn(form.getRawValue() as CheckInFormRawValue | NewCheckInFormRawValue);
  }

  resetForm(form: CheckInFormGroup, checkIn: CheckInFormGroupInput): void {
    const checkInRawValue = this.convertCheckInToCheckInRawValue({ ...this.getFormDefaults(), ...checkIn });
    form.reset(
      {
        ...checkInRawValue,
        id: { value: checkInRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CheckInFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      arrivalDate: currentTime,
      departureDate: currentTime,
      smooking: false,
    };
  }

  private convertCheckInRawValueToCheckIn(rawCheckIn: CheckInFormRawValue | NewCheckInFormRawValue): ICheckIn | NewCheckIn {
    return {
      ...rawCheckIn,
      arrivalDate: dayjs(rawCheckIn.arrivalDate, DATE_TIME_FORMAT),
      departureDate: dayjs(rawCheckIn.departureDate, DATE_TIME_FORMAT),
    };
  }

  private convertCheckInToCheckInRawValue(
    checkIn: ICheckIn | (Partial<NewCheckIn> & CheckInFormDefaults)
  ): CheckInFormRawValue | PartialWithRequiredKeyOf<NewCheckInFormRawValue> {
    return {
      ...checkIn,
      arrivalDate: checkIn.arrivalDate ? checkIn.arrivalDate.format(DATE_TIME_FORMAT) : undefined,
      departureDate: checkIn.departureDate ? checkIn.departureDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
