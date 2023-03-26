import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFicha } from '../ficha.model';
import { FichaService } from '../service/ficha.service';

@Injectable({ providedIn: 'root' })
export class FichaRoutingResolveService implements Resolve<IFicha | null> {
  constructor(protected service: FichaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFicha | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ficha: HttpResponse<IFicha>) => {
          if (ficha.body) {
            return of(ficha.body);
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
