import { IProductRequest, NewProductRequest } from './product-request.model';

export const sampleWithRequiredData: IProductRequest = {
  id: 77158,
};

export const sampleWithPartialData: IProductRequest = {
  id: 85264,
  productUnitPrice: 86453,
  requestedQuantity: 14869,
};

export const sampleWithFullData: IProductRequest = {
  id: 89700,
  productName: 'real-time matrix input',
  productUnitPrice: 62769,
  productTotalPrice: 97678,
  requestedQuantity: 11002,
};

export const sampleWithNewData: NewProductRequest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
