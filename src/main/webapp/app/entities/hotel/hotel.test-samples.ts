import { IHotel, NewHotel } from './hotel.model';

export const sampleWithRequiredData: IHotel = {
  id: 26171,
};

export const sampleWithPartialData: IHotel = {
  id: 31221,
  adresse: 'bypass hour Human',
  starsNumber: 59149,
};

export const sampleWithFullData: IHotel = {
  id: 32633,
  hotelId: 91648,
  name: 'support Developer payment',
  description: 'parse',
  adresse: 'Samoa Self-enabling haptic',
  starsNumber: 56204,
  emergencyNumber: 92390,
};

export const sampleWithNewData: NewHotel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
