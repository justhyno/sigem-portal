import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectoComponent } from '../list/projecto.component';
import { ProjectoDetailComponent } from '../detail/projecto-detail.component';
import { ProjectoUpdateComponent } from '../update/projecto-update.component';
import { ProjectoRoutingResolveService } from './projecto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const projectoRoute: Routes = [
  {
    path: '',
    component: ProjectoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectoDetailComponent,
    resolve: {
      projecto: ProjectoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectoUpdateComponent,
    resolve: {
      projecto: ProjectoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectoUpdateComponent,
    resolve: {
      projecto: ProjectoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(projectoRoute)],
  exports: [RouterModule],
})
export class ProjectoRoutingModule {}
