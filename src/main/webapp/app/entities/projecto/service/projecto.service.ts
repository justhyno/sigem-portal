import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjecto, NewProjecto } from '../projecto.model';

export type PartialUpdateProjecto = Partial<IProjecto> & Pick<IProjecto, 'id'>;

export type EntityResponseType = HttpResponse<IProjecto>;
export type EntityArrayResponseType = HttpResponse<IProjecto[]>;

@Injectable({ providedIn: 'root' })
export class ProjectoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/projectos');
  protected duatUrl = this.resourceUrl + '/titulos';

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projecto: NewProjecto): Observable<EntityResponseType> {
    return this.http.post<IProjecto>(this.resourceUrl, projecto, { observe: 'response' });
  }

  update(projecto: IProjecto): Observable<EntityResponseType> {
    return this.http.put<IProjecto>(`${this.resourceUrl}/${this.getProjectoIdentifier(projecto)}`, projecto, { observe: 'response' });
  }

  partialUpdate(projecto: PartialUpdateProjecto): Observable<EntityResponseType> {
    return this.http.patch<IProjecto>(`${this.resourceUrl}/${this.getProjectoIdentifier(projecto)}`, projecto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProjecto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProjecto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  printTitulo(id: number): Observable<HttpResponse<{}>> {
    return this.http.get(`${this.duatUrl}/${id}`, { observe: 'response' });
  }

  getProjectoIdentifier(projecto: Pick<IProjecto, 'id'>): number {
    return projecto.id;
  }

  compareProjecto(o1: Pick<IProjecto, 'id'> | null, o2: Pick<IProjecto, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectoIdentifier(o1) === this.getProjectoIdentifier(o2) : o1 === o2;
  }

  addProjectoToCollectionIfMissing<Type extends Pick<IProjecto, 'id'>>(
    projectoCollection: Type[],
    ...projectosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projectos: Type[] = projectosToCheck.filter(isPresent);
    if (projectos.length > 0) {
      const projectoCollectionIdentifiers = projectoCollection.map(projectoItem => this.getProjectoIdentifier(projectoItem)!);
      const projectosToAdd = projectos.filter(projectoItem => {
        const projectoIdentifier = this.getProjectoIdentifier(projectoItem);
        if (projectoCollectionIdentifiers.includes(projectoIdentifier)) {
          return false;
        }
        projectoCollectionIdentifiers.push(projectoIdentifier);
        return true;
      });
      return [...projectosToAdd, ...projectoCollection];
    }
    return projectoCollection;
  }
}
