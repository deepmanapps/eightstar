import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParkingAll, NewParkingAll } from '../parking-all.model';

export type PartialUpdateParkingAll = Partial<IParkingAll> & Pick<IParkingAll, 'id'>;

export type EntityResponseType = HttpResponse<IParkingAll>;
export type EntityArrayResponseType = HttpResponse<IParkingAll[]>;

@Injectable({ providedIn: 'root' })
export class ParkingAllService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parking-alls');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parkingAll: NewParkingAll): Observable<EntityResponseType> {
    return this.http.post<IParkingAll>(this.resourceUrl, parkingAll, { observe: 'response' });
  }

  update(parkingAll: IParkingAll): Observable<EntityResponseType> {
    return this.http.put<IParkingAll>(`${this.resourceUrl}/${this.getParkingAllIdentifier(parkingAll)}`, parkingAll, {
      observe: 'response',
    });
  }

  partialUpdate(parkingAll: PartialUpdateParkingAll): Observable<EntityResponseType> {
    return this.http.patch<IParkingAll>(`${this.resourceUrl}/${this.getParkingAllIdentifier(parkingAll)}`, parkingAll, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParkingAll>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParkingAll[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParkingAllIdentifier(parkingAll: Pick<IParkingAll, 'id'>): number {
    return parkingAll.id;
  }

  compareParkingAll(o1: Pick<IParkingAll, 'id'> | null, o2: Pick<IParkingAll, 'id'> | null): boolean {
    return o1 && o2 ? this.getParkingAllIdentifier(o1) === this.getParkingAllIdentifier(o2) : o1 === o2;
  }

  addParkingAllToCollectionIfMissing<Type extends Pick<IParkingAll, 'id'>>(
    parkingAllCollection: Type[],
    ...parkingAllsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const parkingAlls: Type[] = parkingAllsToCheck.filter(isPresent);
    if (parkingAlls.length > 0) {
      const parkingAllCollectionIdentifiers = parkingAllCollection.map(parkingAllItem => this.getParkingAllIdentifier(parkingAllItem)!);
      const parkingAllsToAdd = parkingAlls.filter(parkingAllItem => {
        const parkingAllIdentifier = this.getParkingAllIdentifier(parkingAllItem);
        if (parkingAllCollectionIdentifiers.includes(parkingAllIdentifier)) {
          return false;
        }
        parkingAllCollectionIdentifiers.push(parkingAllIdentifier);
        return true;
      });
      return [...parkingAllsToAdd, ...parkingAllCollection];
    }
    return parkingAllCollection;
  }
}
