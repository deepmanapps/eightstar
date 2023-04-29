import { IParkingAll, NewParkingAll } from './parking-all.model';

export const sampleWithRequiredData: IParkingAll = {
  id: 56157,
};

export const sampleWithPartialData: IParkingAll = {
  id: 69670,
};

export const sampleWithFullData: IParkingAll = {
  id: 44005,
  name: 'recontextualize maximize Usability',
};

export const sampleWithNewData: NewParkingAll = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
