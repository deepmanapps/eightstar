import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IServiceRequest, NewServiceRequest } from '../service-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServiceRequest for edit and NewServiceRequestFormGroupInput for create.
 */
type ServiceRequestFormGroupInput = IServiceRequest | PartialWithRequiredKeyOf<NewServiceRequest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IServiceRequest | NewServiceRequest> = Omit<T, 'requestDate' | 'requestThruDate'> & {
  requestDate?: string | null;
  requestThruDate?: string | null;
};

type ServiceRequestFormRawValue = FormValueOf<IServiceRequest>;

type NewServiceRequestFormRawValue = FormValueOf<NewServiceRequest>;

type ServiceRequestFormDefaults = Pick<NewServiceRequest, 'id' | 'requestDate' | 'requestThruDate' | 'guest'>;

type ServiceRequestFormGroupContent = {
  id: FormControl<ServiceRequestFormRawValue['id'] | NewServiceRequest['id']>;
  requestDate: FormControl<ServiceRequestFormRawValue['requestDate']>;
  requestThruDate: FormControl<ServiceRequestFormRawValue['requestThruDate']>;
  statusRequest: FormControl<ServiceRequestFormRawValue['statusRequest']>;
  rejecttReason: FormControl<ServiceRequestFormRawValue['rejecttReason']>;
  requestDescription: FormControl<ServiceRequestFormRawValue['requestDescription']>;
  objectNumber: FormControl<ServiceRequestFormRawValue['objectNumber']>;
  guest: FormControl<ServiceRequestFormRawValue['guest']>;
  quantity: FormControl<ServiceRequestFormRawValue['quantity']>;
  totalPrice: FormControl<ServiceRequestFormRawValue['totalPrice']>;
  parkingAll: FormControl<ServiceRequestFormRawValue['parkingAll']>;
  deliveryRequestPlace: FormControl<ServiceRequestFormRawValue['deliveryRequestPlace']>;
  services: FormControl<ServiceRequestFormRawValue['services']>;
  checkIn: FormControl<ServiceRequestFormRawValue['checkIn']>;
};

export type ServiceRequestFormGroup = FormGroup<ServiceRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiceRequestFormService {
  createServiceRequestFormGroup(serviceRequest: ServiceRequestFormGroupInput = { id: null }): ServiceRequestFormGroup {
    const serviceRequestRawValue = this.convertServiceRequestToServiceRequestRawValue({
      ...this.getFormDefaults(),
      ...serviceRequest,
    });
    return new FormGroup<ServiceRequestFormGroupContent>({
      id: new FormControl(
        { value: serviceRequestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      requestDate: new FormControl(serviceRequestRawValue.requestDate),
      requestThruDate: new FormControl(serviceRequestRawValue.requestThruDate),
      statusRequest: new FormControl(serviceRequestRawValue.statusRequest),
      rejecttReason: new FormControl(serviceRequestRawValue.rejecttReason),
      requestDescription: new FormControl(serviceRequestRawValue.requestDescription),
      objectNumber: new FormControl(serviceRequestRawValue.objectNumber),
      guest: new FormControl(serviceRequestRawValue.guest),
      quantity: new FormControl(serviceRequestRawValue.quantity),
      totalPrice: new FormControl(serviceRequestRawValue.totalPrice),
      parkingAll: new FormControl(serviceRequestRawValue.parkingAll),
      deliveryRequestPlace: new FormControl(serviceRequestRawValue.deliveryRequestPlace),
      services: new FormControl(serviceRequestRawValue.services),
      checkIn: new FormControl(serviceRequestRawValue.checkIn),
    });
  }

  getServiceRequest(form: ServiceRequestFormGroup): IServiceRequest | NewServiceRequest {
    return this.convertServiceRequestRawValueToServiceRequest(
      form.getRawValue() as ServiceRequestFormRawValue | NewServiceRequestFormRawValue
    );
  }

  resetForm(form: ServiceRequestFormGroup, serviceRequest: ServiceRequestFormGroupInput): void {
    const serviceRequestRawValue = this.convertServiceRequestToServiceRequestRawValue({ ...this.getFormDefaults(), ...serviceRequest });
    form.reset(
      {
        ...serviceRequestRawValue,
        id: { value: serviceRequestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ServiceRequestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      requestDate: currentTime,
      requestThruDate: currentTime,
      guest: false,
    };
  }

  private convertServiceRequestRawValueToServiceRequest(
    rawServiceRequest: ServiceRequestFormRawValue | NewServiceRequestFormRawValue
  ): IServiceRequest | NewServiceRequest {
    return {
      ...rawServiceRequest,
      requestDate: dayjs(rawServiceRequest.requestDate, DATE_TIME_FORMAT),
      requestThruDate: dayjs(rawServiceRequest.requestThruDate, DATE_TIME_FORMAT),
    };
  }

  private convertServiceRequestToServiceRequestRawValue(
    serviceRequest: IServiceRequest | (Partial<NewServiceRequest> & ServiceRequestFormDefaults)
  ): ServiceRequestFormRawValue | PartialWithRequiredKeyOf<NewServiceRequestFormRawValue> {
    return {
      ...serviceRequest,
      requestDate: serviceRequest.requestDate ? serviceRequest.requestDate.format(DATE_TIME_FORMAT) : undefined,
      requestThruDate: serviceRequest.requestThruDate ? serviceRequest.requestThruDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
