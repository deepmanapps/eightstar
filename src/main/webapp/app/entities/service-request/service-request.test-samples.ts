import dayjs from 'dayjs/esm';

import { RQStatus } from 'app/entities/enumerations/rq-status.model';

import { IServiceRequest, NewServiceRequest } from './service-request.model';

export const sampleWithRequiredData: IServiceRequest = {
  id: 10757,
};

export const sampleWithPartialData: IServiceRequest = {
  id: 80235,
  requestDate: dayjs('2023-04-29T03:15'),
  statusRequest: RQStatus['NOK'],
  rejecttReason: 'robust red',
  quantity: 46995,
};

export const sampleWithFullData: IServiceRequest = {
  id: 30592,
  requestDate: dayjs('2023-04-28T11:18'),
  requestThruDate: dayjs('2023-04-28T22:20'),
  statusRequest: RQStatus['INPROGRESS'],
  rejecttReason: 'Senior Sleek program',
  requestDescription: 'bypassing',
  objectNumber: 'Ball Pakistan back-end',
  guest: false,
  quantity: 47300,
  totalPrice: 1291,
};

export const sampleWithNewData: NewServiceRequest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
