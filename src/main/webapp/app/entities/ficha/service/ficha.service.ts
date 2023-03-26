import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFicha, NewFicha } from '../ficha.model';

export type PartialUpdateFicha = Partial<IFicha> & Pick<IFicha, 'id'>;

export type EntityResponseType = HttpResponse<IFicha>;
export type EntityArrayResponseType = HttpResponse<IFicha[]>;

@Injectable({ providedIn: 'root' })
export class FichaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fichas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ficha: NewFicha): Observable<EntityResponseType> {
    return this.http.post<IFicha>(this.resourceUrl, ficha, { observe: 'response' });
  }

  update(ficha: IFicha): Observable<EntityResponseType> {
    return this.http.put<IFicha>(`${this.resourceUrl}/${this.getFichaIdentifier(ficha)}`, ficha, { observe: 'response' });
  }

  partialUpdate(ficha: PartialUpdateFicha): Observable<EntityResponseType> {
    return this.http.patch<IFicha>(`${this.resourceUrl}/${this.getFichaIdentifier(ficha)}`, ficha, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFicha>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFicha[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFichaIdentifier(ficha: Pick<IFicha, 'id'>): number {
    return ficha.id;
  }

  compareFicha(o1: Pick<IFicha, 'id'> | null, o2: Pick<IFicha, 'id'> | null): boolean {
    return o1 && o2 ? this.getFichaIdentifier(o1) === this.getFichaIdentifier(o2) : o1 === o2;
  }

  addFichaToCollectionIfMissing<Type extends Pick<IFicha, 'id'>>(
    fichaCollection: Type[],
    ...fichasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fichas: Type[] = fichasToCheck.filter(isPresent);
    if (fichas.length > 0) {
      const fichaCollectionIdentifiers = fichaCollection.map(fichaItem => this.getFichaIdentifier(fichaItem)!);
      const fichasToAdd = fichas.filter(fichaItem => {
        const fichaIdentifier = this.getFichaIdentifier(fichaItem);
        if (fichaCollectionIdentifiers.includes(fichaIdentifier)) {
          return false;
        }
        fichaCollectionIdentifiers.push(fichaIdentifier);
        return true;
      });
      return [...fichasToAdd, ...fichaCollection];
    }
    return fichaCollection;
  }
}
