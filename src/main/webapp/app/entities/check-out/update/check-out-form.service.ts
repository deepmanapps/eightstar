import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICheckOut, NewCheckOut } from '../check-out.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICheckOut for edit and NewCheckOutFormGroupInput for create.
 */
type CheckOutFormGroupInput = ICheckOut | PartialWithRequiredKeyOf<NewCheckOut>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICheckOut | NewCheckOut> = Omit<T, 'lateCheckOut'> & {
  lateCheckOut?: string | null;
};

type CheckOutFormRawValue = FormValueOf<ICheckOut>;

type NewCheckOutFormRawValue = FormValueOf<NewCheckOut>;

type CheckOutFormDefaults = Pick<NewCheckOut, 'id' | 'lateCheckOut' | 'isLate' | 'isCollectedDepositAmount'>;

type CheckOutFormGroupContent = {
  id: FormControl<CheckOutFormRawValue['id'] | NewCheckOut['id']>;
  roomClearance: FormControl<CheckOutFormRawValue['roomClearance']>;
  customerReview: FormControl<CheckOutFormRawValue['customerReview']>;
  miniBarClearance: FormControl<CheckOutFormRawValue['miniBarClearance']>;
  lateCheckOut: FormControl<CheckOutFormRawValue['lateCheckOut']>;
  isLate: FormControl<CheckOutFormRawValue['isLate']>;
  isCollectedDepositAmount: FormControl<CheckOutFormRawValue['isCollectedDepositAmount']>;
  collectedAmount: FormControl<CheckOutFormRawValue['collectedAmount']>;
};

export type CheckOutFormGroup = FormGroup<CheckOutFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CheckOutFormService {
  createCheckOutFormGroup(checkOut: CheckOutFormGroupInput = { id: null }): CheckOutFormGroup {
    const checkOutRawValue = this.convertCheckOutToCheckOutRawValue({
      ...this.getFormDefaults(),
      ...checkOut,
    });
    return new FormGroup<CheckOutFormGroupContent>({
      id: new FormControl(
        { value: checkOutRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      roomClearance: new FormControl(checkOutRawValue.roomClearance),
      customerReview: new FormControl(checkOutRawValue.customerReview),
      miniBarClearance: new FormControl(checkOutRawValue.miniBarClearance),
      lateCheckOut: new FormControl(checkOutRawValue.lateCheckOut),
      isLate: new FormControl(checkOutRawValue.isLate),
      isCollectedDepositAmount: new FormControl(checkOutRawValue.isCollectedDepositAmount),
      collectedAmount: new FormControl(checkOutRawValue.collectedAmount),
    });
  }

  getCheckOut(form: CheckOutFormGroup): ICheckOut | NewCheckOut {
    return this.convertCheckOutRawValueToCheckOut(form.getRawValue() as CheckOutFormRawValue | NewCheckOutFormRawValue);
  }

  resetForm(form: CheckOutFormGroup, checkOut: CheckOutFormGroupInput): void {
    const checkOutRawValue = this.convertCheckOutToCheckOutRawValue({ ...this.getFormDefaults(), ...checkOut });
    form.reset(
      {
        ...checkOutRawValue,
        id: { value: checkOutRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CheckOutFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lateCheckOut: currentTime,
      isLate: false,
      isCollectedDepositAmount: false,
    };
  }

  private convertCheckOutRawValueToCheckOut(rawCheckOut: CheckOutFormRawValue | NewCheckOutFormRawValue): ICheckOut | NewCheckOut {
    return {
      ...rawCheckOut,
      lateCheckOut: dayjs(rawCheckOut.lateCheckOut, DATE_TIME_FORMAT),
    };
  }

  private convertCheckOutToCheckOutRawValue(
    checkOut: ICheckOut | (Partial<NewCheckOut> & CheckOutFormDefaults)
  ): CheckOutFormRawValue | PartialWithRequiredKeyOf<NewCheckOutFormRawValue> {
    return {
      ...checkOut,
      lateCheckOut: checkOut.lateCheckOut ? checkOut.lateCheckOut.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
