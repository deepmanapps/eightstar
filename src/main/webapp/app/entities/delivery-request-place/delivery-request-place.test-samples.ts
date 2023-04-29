import { IDeliveryRequestPlace, NewDeliveryRequestPlace } from './delivery-request-place.model';

export const sampleWithRequiredData: IDeliveryRequestPlace = {
  id: 73598,
};

export const sampleWithPartialData: IDeliveryRequestPlace = {
  id: 79296,
};

export const sampleWithFullData: IDeliveryRequestPlace = {
  id: 46278,
  name: 'Bermuda Zealand',
};

export const sampleWithNewData: NewDeliveryRequestPlace = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
