import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 91847,
};

export const sampleWithPartialData: ILocation = {
  id: 94148,
  latitude: 'Brand capacitor',
};

export const sampleWithFullData: ILocation = {
  id: 53844,
  longitude: 'open 1080p',
  latitude: 'hard Slovakia Directives',
};

export const sampleWithNewData: NewLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
