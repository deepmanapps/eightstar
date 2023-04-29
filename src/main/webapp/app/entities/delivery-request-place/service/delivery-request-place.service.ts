import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeliveryRequestPlace, NewDeliveryRequestPlace } from '../delivery-request-place.model';

export type PartialUpdateDeliveryRequestPlace = Partial<IDeliveryRequestPlace> & Pick<IDeliveryRequestPlace, 'id'>;

export type EntityResponseType = HttpResponse<IDeliveryRequestPlace>;
export type EntityArrayResponseType = HttpResponse<IDeliveryRequestPlace[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryRequestPlaceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/delivery-request-places');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(deliveryRequestPlace: NewDeliveryRequestPlace): Observable<EntityResponseType> {
    return this.http.post<IDeliveryRequestPlace>(this.resourceUrl, deliveryRequestPlace, { observe: 'response' });
  }

  update(deliveryRequestPlace: IDeliveryRequestPlace): Observable<EntityResponseType> {
    return this.http.put<IDeliveryRequestPlace>(
      `${this.resourceUrl}/${this.getDeliveryRequestPlaceIdentifier(deliveryRequestPlace)}`,
      deliveryRequestPlace,
      { observe: 'response' }
    );
  }

  partialUpdate(deliveryRequestPlace: PartialUpdateDeliveryRequestPlace): Observable<EntityResponseType> {
    return this.http.patch<IDeliveryRequestPlace>(
      `${this.resourceUrl}/${this.getDeliveryRequestPlaceIdentifier(deliveryRequestPlace)}`,
      deliveryRequestPlace,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeliveryRequestPlace>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeliveryRequestPlace[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDeliveryRequestPlaceIdentifier(deliveryRequestPlace: Pick<IDeliveryRequestPlace, 'id'>): number {
    return deliveryRequestPlace.id;
  }

  compareDeliveryRequestPlace(o1: Pick<IDeliveryRequestPlace, 'id'> | null, o2: Pick<IDeliveryRequestPlace, 'id'> | null): boolean {
    return o1 && o2 ? this.getDeliveryRequestPlaceIdentifier(o1) === this.getDeliveryRequestPlaceIdentifier(o2) : o1 === o2;
  }

  addDeliveryRequestPlaceToCollectionIfMissing<Type extends Pick<IDeliveryRequestPlace, 'id'>>(
    deliveryRequestPlaceCollection: Type[],
    ...deliveryRequestPlacesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const deliveryRequestPlaces: Type[] = deliveryRequestPlacesToCheck.filter(isPresent);
    if (deliveryRequestPlaces.length > 0) {
      const deliveryRequestPlaceCollectionIdentifiers = deliveryRequestPlaceCollection.map(
        deliveryRequestPlaceItem => this.getDeliveryRequestPlaceIdentifier(deliveryRequestPlaceItem)!
      );
      const deliveryRequestPlacesToAdd = deliveryRequestPlaces.filter(deliveryRequestPlaceItem => {
        const deliveryRequestPlaceIdentifier = this.getDeliveryRequestPlaceIdentifier(deliveryRequestPlaceItem);
        if (deliveryRequestPlaceCollectionIdentifiers.includes(deliveryRequestPlaceIdentifier)) {
          return false;
        }
        deliveryRequestPlaceCollectionIdentifiers.push(deliveryRequestPlaceIdentifier);
        return true;
      });
      return [...deliveryRequestPlacesToAdd, ...deliveryRequestPlaceCollection];
    }
    return deliveryRequestPlaceCollection;
  }
}
