import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotelServices, NewHotelServices } from '../hotel-services.model';

export type PartialUpdateHotelServices = Partial<IHotelServices> & Pick<IHotelServices, 'id'>;

export type EntityResponseType = HttpResponse<IHotelServices>;
export type EntityArrayResponseType = HttpResponse<IHotelServices[]>;

@Injectable({ providedIn: 'root' })
export class HotelServicesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hotel-services');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hotelServices: NewHotelServices): Observable<EntityResponseType> {
    return this.http.post<IHotelServices>(this.resourceUrl, hotelServices, { observe: 'response' });
  }

  update(hotelServices: IHotelServices): Observable<EntityResponseType> {
    return this.http.put<IHotelServices>(`${this.resourceUrl}/${this.getHotelServicesIdentifier(hotelServices)}`, hotelServices, {
      observe: 'response',
    });
  }

  partialUpdate(hotelServices: PartialUpdateHotelServices): Observable<EntityResponseType> {
    return this.http.patch<IHotelServices>(`${this.resourceUrl}/${this.getHotelServicesIdentifier(hotelServices)}`, hotelServices, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHotelServices>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHotelServices[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHotelServicesIdentifier(hotelServices: Pick<IHotelServices, 'id'>): number {
    return hotelServices.id;
  }

  compareHotelServices(o1: Pick<IHotelServices, 'id'> | null, o2: Pick<IHotelServices, 'id'> | null): boolean {
    return o1 && o2 ? this.getHotelServicesIdentifier(o1) === this.getHotelServicesIdentifier(o2) : o1 === o2;
  }

  addHotelServicesToCollectionIfMissing<Type extends Pick<IHotelServices, 'id'>>(
    hotelServicesCollection: Type[],
    ...hotelServicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hotelServices: Type[] = hotelServicesToCheck.filter(isPresent);
    if (hotelServices.length > 0) {
      const hotelServicesCollectionIdentifiers = hotelServicesCollection.map(
        hotelServicesItem => this.getHotelServicesIdentifier(hotelServicesItem)!
      );
      const hotelServicesToAdd = hotelServices.filter(hotelServicesItem => {
        const hotelServicesIdentifier = this.getHotelServicesIdentifier(hotelServicesItem);
        if (hotelServicesCollectionIdentifiers.includes(hotelServicesIdentifier)) {
          return false;
        }
        hotelServicesCollectionIdentifiers.push(hotelServicesIdentifier);
        return true;
      });
      return [...hotelServicesToAdd, ...hotelServicesCollection];
    }
    return hotelServicesCollection;
  }
}
