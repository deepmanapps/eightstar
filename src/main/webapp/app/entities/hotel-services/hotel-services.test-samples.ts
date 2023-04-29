import { IHotelServices, NewHotelServices } from './hotel-services.model';

export const sampleWithRequiredData: IHotelServices = {
  id: 39823,
};

export const sampleWithPartialData: IHotelServices = {
  id: 66511,
  forGuest: false,
  servicePrice: 27564,
};

export const sampleWithFullData: IHotelServices = {
  id: 3167,
  active: true,
  forGuest: false,
  servicePrice: 77669,
};

export const sampleWithNewData: NewHotelServices = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
