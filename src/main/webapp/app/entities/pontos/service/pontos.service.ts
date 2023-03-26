import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPontos, NewPontos } from '../pontos.model';

export type PartialUpdatePontos = Partial<IPontos> & Pick<IPontos, 'id'>;

export type EntityResponseType = HttpResponse<IPontos>;
export type EntityArrayResponseType = HttpResponse<IPontos[]>;

@Injectable({ providedIn: 'root' })
export class PontosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pontos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pontos: NewPontos): Observable<EntityResponseType> {
    return this.http.post<IPontos>(this.resourceUrl, pontos, { observe: 'response' });
  }

  update(pontos: IPontos): Observable<EntityResponseType> {
    return this.http.put<IPontos>(`${this.resourceUrl}/${this.getPontosIdentifier(pontos)}`, pontos, { observe: 'response' });
  }

  partialUpdate(pontos: PartialUpdatePontos): Observable<EntityResponseType> {
    return this.http.patch<IPontos>(`${this.resourceUrl}/${this.getPontosIdentifier(pontos)}`, pontos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPontos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPontos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPontosIdentifier(pontos: Pick<IPontos, 'id'>): number {
    return pontos.id;
  }

  comparePontos(o1: Pick<IPontos, 'id'> | null, o2: Pick<IPontos, 'id'> | null): boolean {
    return o1 && o2 ? this.getPontosIdentifier(o1) === this.getPontosIdentifier(o2) : o1 === o2;
  }

  addPontosToCollectionIfMissing<Type extends Pick<IPontos, 'id'>>(
    pontosCollection: Type[],
    ...pontosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pontos: Type[] = pontosToCheck.filter(isPresent);
    if (pontos.length > 0) {
      const pontosCollectionIdentifiers = pontosCollection.map(pontosItem => this.getPontosIdentifier(pontosItem)!);
      const pontosToAdd = pontos.filter(pontosItem => {
        const pontosIdentifier = this.getPontosIdentifier(pontosItem);
        if (pontosCollectionIdentifiers.includes(pontosIdentifier)) {
          return false;
        }
        pontosCollectionIdentifiers.push(pontosIdentifier);
        return true;
      });
      return [...pontosToAdd, ...pontosCollection];
    }
    return pontosCollection;
  }
}
