import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'projecto',
        data: { pageTitle: 'sigemPortalApp.projecto.home.title' },
        loadChildren: () => import('./projecto/projecto.module').then(m => m.ProjectoModule),
      },
      {
        path: 'ficha',
        data: { pageTitle: 'sigemPortalApp.ficha.home.title' },
        loadChildren: () => import('./ficha/ficha.module').then(m => m.FichaModule),
      },
      {
        path: 'alfa',
        data: { pageTitle: 'sigemPortalApp.alfa.home.title' },
        loadChildren: () => import('./alfa/alfa.module').then(m => m.AlfaModule),
      },
      {
        path: 'spatial-unit',
        data: { pageTitle: 'sigemPortalApp.spatialUnit.home.title' },
        loadChildren: () => import('./spatial-unit/spatial-unit.module').then(m => m.SpatialUnitModule),
      },
      {
        path: 'pontos',
        data: { pageTitle: 'sigemPortalApp.pontos.home.title' },
        loadChildren: () => import('./pontos/pontos.module').then(m => m.PontosModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
