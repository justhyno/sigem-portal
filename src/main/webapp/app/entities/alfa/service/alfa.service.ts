import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlfa, NewAlfa } from '../alfa.model';

export type PartialUpdateAlfa = Partial<IAlfa> & Pick<IAlfa, 'id'>;

type RestOf<T extends IAlfa | NewAlfa> = Omit<T, 'dataNascimento' | 'dataLevantamento'> & {
  dataNascimento?: string | null;
  dataLevantamento?: string | null;
};

export type RestAlfa = RestOf<IAlfa>;

export type NewRestAlfa = RestOf<NewAlfa>;

export type PartialUpdateRestAlfa = RestOf<PartialUpdateAlfa>;

export type EntityResponseType = HttpResponse<IAlfa>;
export type EntityArrayResponseType = HttpResponse<IAlfa[]>;

@Injectable({ providedIn: 'root' })
export class AlfaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/alfas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(alfa: NewAlfa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alfa);
    return this.http.post<RestAlfa>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(alfa: IAlfa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alfa);
    return this.http
      .put<RestAlfa>(`${this.resourceUrl}/${this.getAlfaIdentifier(alfa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(alfa: PartialUpdateAlfa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alfa);
    return this.http
      .patch<RestAlfa>(`${this.resourceUrl}/${this.getAlfaIdentifier(alfa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAlfa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAlfa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAlfaIdentifier(alfa: Pick<IAlfa, 'id'>): number {
    return alfa.id;
  }

  compareAlfa(o1: Pick<IAlfa, 'id'> | null, o2: Pick<IAlfa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAlfaIdentifier(o1) === this.getAlfaIdentifier(o2) : o1 === o2;
  }

  addAlfaToCollectionIfMissing<Type extends Pick<IAlfa, 'id'>>(
    alfaCollection: Type[],
    ...alfasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const alfas: Type[] = alfasToCheck.filter(isPresent);
    if (alfas.length > 0) {
      const alfaCollectionIdentifiers = alfaCollection.map(alfaItem => this.getAlfaIdentifier(alfaItem)!);
      const alfasToAdd = alfas.filter(alfaItem => {
        const alfaIdentifier = this.getAlfaIdentifier(alfaItem);
        if (alfaCollectionIdentifiers.includes(alfaIdentifier)) {
          return false;
        }
        alfaCollectionIdentifiers.push(alfaIdentifier);
        return true;
      });
      return [...alfasToAdd, ...alfaCollection];
    }
    return alfaCollection;
  }

  protected convertDateFromClient<T extends IAlfa | NewAlfa | PartialUpdateAlfa>(alfa: T): RestOf<T> {
    return {
      ...alfa,
      dataNascimento: alfa.dataNascimento?.format(DATE_FORMAT) ?? null,
      dataLevantamento: alfa.dataLevantamento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAlfa: RestAlfa): IAlfa {
    return {
      ...restAlfa,
      dataNascimento: restAlfa.dataNascimento ? dayjs(restAlfa.dataNascimento) : undefined,
      dataLevantamento: restAlfa.dataLevantamento ? dayjs(restAlfa.dataLevantamento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAlfa>): HttpResponse<IAlfa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAlfa[]>): HttpResponse<IAlfa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
