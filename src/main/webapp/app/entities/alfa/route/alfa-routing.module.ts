import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AlfaComponent } from '../list/alfa.component';
import { AlfaDetailComponent } from '../detail/alfa-detail.component';
import { AlfaUpdateComponent } from '../update/alfa-update.component';
import { AlfaRoutingResolveService } from './alfa-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const alfaRoute: Routes = [
  {
    path: '',
    component: AlfaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlfaDetailComponent,
    resolve: {
      alfa: AlfaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlfaUpdateComponent,
    resolve: {
      alfa: AlfaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlfaUpdateComponent,
    resolve: {
      alfa: AlfaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(alfaRoute)],
  exports: [RouterModule],
})
export class AlfaRoutingModule {}
