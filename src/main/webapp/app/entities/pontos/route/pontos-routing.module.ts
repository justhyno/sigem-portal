import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PontosComponent } from '../list/pontos.component';
import { PontosDetailComponent } from '../detail/pontos-detail.component';
import { PontosUpdateComponent } from '../update/pontos-update.component';
import { PontosRoutingResolveService } from './pontos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pontosRoute: Routes = [
  {
    path: '',
    component: PontosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PontosDetailComponent,
    resolve: {
      pontos: PontosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PontosUpdateComponent,
    resolve: {
      pontos: PontosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PontosUpdateComponent,
    resolve: {
      pontos: PontosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pontosRoute)],
  exports: [RouterModule],
})
export class PontosRoutingModule {}
