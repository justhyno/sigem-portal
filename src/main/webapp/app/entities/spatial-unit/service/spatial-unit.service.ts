import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISpatialUnit, NewSpatialUnit } from '../spatial-unit.model';

export type PartialUpdateSpatialUnit = Partial<ISpatialUnit> & Pick<ISpatialUnit, 'id'>;

export type EntityResponseType = HttpResponse<ISpatialUnit>;
export type EntityArrayResponseType = HttpResponse<ISpatialUnit[]>;

@Injectable({ providedIn: 'root' })
export class SpatialUnitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/spatial-units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(spatialUnit: NewSpatialUnit): Observable<EntityResponseType> {
    return this.http.post<ISpatialUnit>(this.resourceUrl, spatialUnit, { observe: 'response' });
  }

  update(spatialUnit: ISpatialUnit): Observable<EntityResponseType> {
    return this.http.put<ISpatialUnit>(`${this.resourceUrl}/${this.getSpatialUnitIdentifier(spatialUnit)}`, spatialUnit, {
      observe: 'response',
    });
  }

  partialUpdate(spatialUnit: PartialUpdateSpatialUnit): Observable<EntityResponseType> {
    return this.http.patch<ISpatialUnit>(`${this.resourceUrl}/${this.getSpatialUnitIdentifier(spatialUnit)}`, spatialUnit, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpatialUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpatialUnit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSpatialUnitIdentifier(spatialUnit: Pick<ISpatialUnit, 'id'>): number {
    return spatialUnit.id;
  }

  compareSpatialUnit(o1: Pick<ISpatialUnit, 'id'> | null, o2: Pick<ISpatialUnit, 'id'> | null): boolean {
    return o1 && o2 ? this.getSpatialUnitIdentifier(o1) === this.getSpatialUnitIdentifier(o2) : o1 === o2;
  }

  addSpatialUnitToCollectionIfMissing<Type extends Pick<ISpatialUnit, 'id'>>(
    spatialUnitCollection: Type[],
    ...spatialUnitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const spatialUnits: Type[] = spatialUnitsToCheck.filter(isPresent);
    if (spatialUnits.length > 0) {
      const spatialUnitCollectionIdentifiers = spatialUnitCollection.map(
        spatialUnitItem => this.getSpatialUnitIdentifier(spatialUnitItem)!
      );
      const spatialUnitsToAdd = spatialUnits.filter(spatialUnitItem => {
        const spatialUnitIdentifier = this.getSpatialUnitIdentifier(spatialUnitItem);
        if (spatialUnitCollectionIdentifiers.includes(spatialUnitIdentifier)) {
          return false;
        }
        spatialUnitCollectionIdentifiers.push(spatialUnitIdentifier);
        return true;
      });
      return [...spatialUnitsToAdd, ...spatialUnitCollection];
    }
    return spatialUnitCollection;
  }
}
