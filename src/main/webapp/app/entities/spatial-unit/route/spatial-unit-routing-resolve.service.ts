import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISpatialUnit } from '../spatial-unit.model';
import { SpatialUnitService } from '../service/spatial-unit.service';

@Injectable({ providedIn: 'root' })
export class SpatialUnitRoutingResolveService implements Resolve<ISpatialUnit | null> {
  constructor(protected service: SpatialUnitService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpatialUnit | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((spatialUnit: HttpResponse<ISpatialUnit>) => {
          if (spatialUnit.body) {
            return of(spatialUnit.body);
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
