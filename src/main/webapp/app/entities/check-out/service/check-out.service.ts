import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICheckOut, NewCheckOut } from '../check-out.model';

export type PartialUpdateCheckOut = Partial<ICheckOut> & Pick<ICheckOut, 'id'>;

type RestOf<T extends ICheckOut | NewCheckOut> = Omit<T, 'lateCheckOut'> & {
  lateCheckOut?: string | null;
};

export type RestCheckOut = RestOf<ICheckOut>;

export type NewRestCheckOut = RestOf<NewCheckOut>;

export type PartialUpdateRestCheckOut = RestOf<PartialUpdateCheckOut>;

export type EntityResponseType = HttpResponse<ICheckOut>;
export type EntityArrayResponseType = HttpResponse<ICheckOut[]>;

@Injectable({ providedIn: 'root' })
export class CheckOutService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/check-outs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(checkOut: NewCheckOut): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkOut);
    return this.http
      .post<RestCheckOut>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(checkOut: ICheckOut): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkOut);
    return this.http
      .put<RestCheckOut>(`${this.resourceUrl}/${this.getCheckOutIdentifier(checkOut)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(checkOut: PartialUpdateCheckOut): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkOut);
    return this.http
      .patch<RestCheckOut>(`${this.resourceUrl}/${this.getCheckOutIdentifier(checkOut)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCheckOut>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCheckOut[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCheckOutIdentifier(checkOut: Pick<ICheckOut, 'id'>): number {
    return checkOut.id;
  }

  compareCheckOut(o1: Pick<ICheckOut, 'id'> | null, o2: Pick<ICheckOut, 'id'> | null): boolean {
    return o1 && o2 ? this.getCheckOutIdentifier(o1) === this.getCheckOutIdentifier(o2) : o1 === o2;
  }

  addCheckOutToCollectionIfMissing<Type extends Pick<ICheckOut, 'id'>>(
    checkOutCollection: Type[],
    ...checkOutsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const checkOuts: Type[] = checkOutsToCheck.filter(isPresent);
    if (checkOuts.length > 0) {
      const checkOutCollectionIdentifiers = checkOutCollection.map(checkOutItem => this.getCheckOutIdentifier(checkOutItem)!);
      const checkOutsToAdd = checkOuts.filter(checkOutItem => {
        const checkOutIdentifier = this.getCheckOutIdentifier(checkOutItem);
        if (checkOutCollectionIdentifiers.includes(checkOutIdentifier)) {
          return false;
        }
        checkOutCollectionIdentifiers.push(checkOutIdentifier);
        return true;
      });
      return [...checkOutsToAdd, ...checkOutCollection];
    }
    return checkOutCollection;
  }

  protected convertDateFromClient<T extends ICheckOut | NewCheckOut | PartialUpdateCheckOut>(checkOut: T): RestOf<T> {
    return {
      ...checkOut,
      lateCheckOut: checkOut.lateCheckOut?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCheckOut: RestCheckOut): ICheckOut {
    return {
      ...restCheckOut,
      lateCheckOut: restCheckOut.lateCheckOut ? dayjs(restCheckOut.lateCheckOut) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCheckOut>): HttpResponse<ICheckOut> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCheckOut[]>): HttpResponse<ICheckOut[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
