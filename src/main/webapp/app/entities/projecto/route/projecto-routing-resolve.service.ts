import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProjecto } from '../projecto.model';
import { ProjectoService } from '../service/projecto.service';

@Injectable({ providedIn: 'root' })
export class ProjectoRoutingResolveService implements Resolve<IProjecto | null> {
  constructor(protected service: ProjectoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjecto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((projecto: HttpResponse<IProjecto>) => {
          if (projecto.body) {
            return of(projecto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
