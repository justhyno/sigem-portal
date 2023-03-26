import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SpatialUnitComponent } from '../list/spatial-unit.component';
import { SpatialUnitDetailComponent } from '../detail/spatial-unit-detail.component';
import { SpatialUnitUpdateComponent } from '../update/spatial-unit-update.component';
import { SpatialUnitRoutingResolveService } from './spatial-unit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const spatialUnitRoute: Routes = [
  {
    path: '',
    component: SpatialUnitComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SpatialUnitDetailComponent,
    resolve: {
      spatialUnit: SpatialUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SpatialUnitUpdateComponent,
    resolve: {
      spatialUnit: SpatialUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SpatialUnitUpdateComponent,
    resolve: {
      spatialUnit: SpatialUnitRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(spatialUnitRoute)],
  exports: [RouterModule],
})
export class SpatialUnitRoutingModule {}
