import { IServiceRequest } from 'app/entities/service-request/service-request.model';

export interface IProductRequest {
  id: number;
  productName?: string | null;
  productUnitPrice?: number | null;
  productTotalPrice?: number | null;
  requestedQuantity?: number | null;
  serviceRequest?: Pick<IServiceRequest, 'id'> | null;
}

export type NewProductRequest = Omit<IProductRequest, 'id'> & { id: null };
