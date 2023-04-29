import dayjs from 'dayjs/esm';

import { Status } from 'app/entities/enumerations/status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

import { ICheckIn, NewCheckIn } from './check-in.model';

export const sampleWithRequiredData: ICheckIn = {
  id: 41177,
};

export const sampleWithPartialData: ICheckIn = {
  id: 75515,
  identityPath: 'disintermediate',
  status: Status['CONFIRMED'],
  arrivalDate: dayjs('2023-04-29T00:25'),
  departureDate: dayjs('2023-04-28T18:18'),
  roomType: 'Guilder',
  smooking: true,
  children: 52173,
  notes: 'stable deliver',
};

export const sampleWithFullData: ICheckIn = {
  id: 70220,
  identityPath: 'USB',
  status: Status['CANCELLED'],
  depositAmount: 14340,
  paymentMethod: PaymentMethod['CASH'],
  arrivalDate: dayjs('2023-04-29T09:48'),
  departureDate: dayjs('2023-04-29T10:02'),
  roomType: 'Graphical blue',
  smooking: true,
  adults: 94680,
  children: 65741,
  notes: 'Tuna',
};

export const sampleWithNewData: NewCheckIn = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
