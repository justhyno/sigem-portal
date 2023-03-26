import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SpatialUnitComponent } from './list/spatial-unit.component';
import { SpatialUnitDetailComponent } from './detail/spatial-unit-detail.component';
import { SpatialUnitUpdateComponent } from './update/spatial-unit-update.component';
import { SpatialUnitDeleteDialogComponent } from './delete/spatial-unit-delete-dialog.component';
import { SpatialUnitRoutingModule } from './route/spatial-unit-routing.module';

@NgModule({
  imports: [SharedModule, SpatialUnitRoutingModule],
  declarations: [SpatialUnitComponent, SpatialUnitDetailComponent, SpatialUnitUpdateComponent, SpatialUnitDeleteDialogComponent],
})
export class SpatialUnitModule {}
