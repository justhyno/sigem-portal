import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProjectoComponent } from './list/projecto.component';
import { ProjectoDetailComponent } from './detail/projecto-detail.component';
import { ProjectoUpdateComponent } from './update/projecto-update.component';
import { ProjectoDeleteDialogComponent } from './delete/projecto-delete-dialog.component';
import { ProjectoRoutingModule } from './route/projecto-routing.module';

@NgModule({
  imports: [SharedModule, ProjectoRoutingModule],
  declarations: [ProjectoComponent, ProjectoDetailComponent, ProjectoUpdateComponent, ProjectoDeleteDialogComponent],
})
export class ProjectoModule {}
