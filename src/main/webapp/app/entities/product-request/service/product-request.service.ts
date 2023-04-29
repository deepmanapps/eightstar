import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductRequest, NewProductRequest } from '../product-request.model';

export type PartialUpdateProductRequest = Partial<IProductRequest> & Pick<IProductRequest, 'id'>;

export type EntityResponseType = HttpResponse<IProductRequest>;
export type EntityArrayResponseType = HttpResponse<IProductRequest[]>;

@Injectable({ providedIn: 'root' })
export class ProductRequestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-requests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productRequest: NewProductRequest): Observable<EntityResponseType> {
    return this.http.post<IProductRequest>(this.resourceUrl, productRequest, { observe: 'response' });
  }

  update(productRequest: IProductRequest): Observable<EntityResponseType> {
    return this.http.put<IProductRequest>(`${this.resourceUrl}/${this.getProductRequestIdentifier(productRequest)}`, productRequest, {
      observe: 'response',
    });
  }

  partialUpdate(productRequest: PartialUpdateProductRequest): Observable<EntityResponseType> {
    return this.http.patch<IProductRequest>(`${this.resourceUrl}/${this.getProductRequestIdentifier(productRequest)}`, productRequest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductRequest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductRequestIdentifier(productRequest: Pick<IProductRequest, 'id'>): number {
    return productRequest.id;
  }

  compareProductRequest(o1: Pick<IProductRequest, 'id'> | null, o2: Pick<IProductRequest, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductRequestIdentifier(o1) === this.getProductRequestIdentifier(o2) : o1 === o2;
  }

  addProductRequestToCollectionIfMissing<Type extends Pick<IProductRequest, 'id'>>(
    productRequestCollection: Type[],
    ...productRequestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productRequests: Type[] = productRequestsToCheck.filter(isPresent);
    if (productRequests.length > 0) {
      const productRequestCollectionIdentifiers = productRequestCollection.map(
        productRequestItem => this.getProductRequestIdentifier(productRequestItem)!
      );
      const productRequestsToAdd = productRequests.filter(productRequestItem => {
        const productRequestIdentifier = this.getProductRequestIdentifier(productRequestItem);
        if (productRequestCollectionIdentifiers.includes(productRequestIdentifier)) {
          return false;
        }
        productRequestCollectionIdentifiers.push(productRequestIdentifier);
        return true;
      });
      return [...productRequestsToAdd, ...productRequestCollection];
    }
    return productRequestCollection;
  }
}
