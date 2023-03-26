import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPontos } from '../pontos.model';
import { PontosService } from '../service/pontos.service';

@Injectable({ providedIn: 'root' })
export class PontosRoutingResolveService implements Resolve<IPontos | null> {
  constructor(protected service: PontosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPontos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pontos: HttpResponse<IPontos>) => {
          if (pontos.body) {
            return of(pontos.body);
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
