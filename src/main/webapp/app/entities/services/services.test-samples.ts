import { IServices, NewServices } from './services.model';

export const sampleWithRequiredData: IServices = {
  id: 10413,
};

export const sampleWithPartialData: IServices = {
  id: 75622,
  nom: 'maroon indexing Euro',
  description: 'withdrawal',
};

export const sampleWithFullData: IServices = {
  id: 30159,
  nom: 'monitor EXE',
  description: 'panel',
};

export const sampleWithNewData: NewServices = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
