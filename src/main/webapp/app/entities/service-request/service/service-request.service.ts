import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServiceRequest, NewServiceRequest } from '../service-request.model';

export type PartialUpdateServiceRequest = Partial<IServiceRequest> & Pick<IServiceRequest, 'id'>;

type RestOf<T extends IServiceRequest | NewServiceRequest> = Omit<T, 'requestDate' | 'requestThruDate'> & {
  requestDate?: string | null;
  requestThruDate?: string | null;
};

export type RestServiceRequest = RestOf<IServiceRequest>;

export type NewRestServiceRequest = RestOf<NewServiceRequest>;

export type PartialUpdateRestServiceRequest = RestOf<PartialUpdateServiceRequest>;

export type EntityResponseType = HttpResponse<IServiceRequest>;
export type EntityArrayResponseType = HttpResponse<IServiceRequest[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRequestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/service-requests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(serviceRequest: NewServiceRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRequest);
    return this.http
      .post<RestServiceRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(serviceRequest: IServiceRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRequest);
    return this.http
      .put<RestServiceRequest>(`${this.resourceUrl}/${this.getServiceRequestIdentifier(serviceRequest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(serviceRequest: PartialUpdateServiceRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRequest);
    return this.http
      .patch<RestServiceRequest>(`${this.resourceUrl}/${this.getServiceRequestIdentifier(serviceRequest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestServiceRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestServiceRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServiceRequestIdentifier(serviceRequest: Pick<IServiceRequest, 'id'>): number {
    return serviceRequest.id;
  }

  compareServiceRequest(o1: Pick<IServiceRequest, 'id'> | null, o2: Pick<IServiceRequest, 'id'> | null): boolean {
    return o1 && o2 ? this.getServiceRequestIdentifier(o1) === this.getServiceRequestIdentifier(o2) : o1 === o2;
  }

  addServiceRequestToCollectionIfMissing<Type extends Pick<IServiceRequest, 'id'>>(
    serviceRequestCollection: Type[],
    ...serviceRequestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const serviceRequests: Type[] = serviceRequestsToCheck.filter(isPresent);
    if (serviceRequests.length > 0) {
      const serviceRequestCollectionIdentifiers = serviceRequestCollection.map(
        serviceRequestItem => this.getServiceRequestIdentifier(serviceRequestItem)!
      );
      const serviceRequestsToAdd = serviceRequests.filter(serviceRequestItem => {
        const serviceRequestIdentifier = this.getServiceRequestIdentifier(serviceRequestItem);
        if (serviceRequestCollectionIdentifiers.includes(serviceRequestIdentifier)) {
          return false;
        }
        serviceRequestCollectionIdentifiers.push(serviceRequestIdentifier);
        return true;
      });
      return [...serviceRequestsToAdd, ...serviceRequestCollection];
    }
    return serviceRequestCollection;
  }

  protected convertDateFromClient<T extends IServiceRequest | NewServiceRequest | PartialUpdateServiceRequest>(
    serviceRequest: T
  ): RestOf<T> {
    return {
      ...serviceRequest,
      requestDate: serviceRequest.requestDate?.toJSON() ?? null,
      requestThruDate: serviceRequest.requestThruDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restServiceRequest: RestServiceRequest): IServiceRequest {
    return {
      ...restServiceRequest,
      requestDate: restServiceRequest.requestDate ? dayjs(restServiceRequest.requestDate) : undefined,
      requestThruDate: restServiceRequest.requestThruDate ? dayjs(restServiceRequest.requestThruDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestServiceRequest>): HttpResponse<IServiceRequest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestServiceRequest[]>): HttpResponse<IServiceRequest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
