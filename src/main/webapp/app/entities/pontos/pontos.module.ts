import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PontosComponent } from './list/pontos.component';
import { PontosDetailComponent } from './detail/pontos-detail.component';
import { PontosUpdateComponent } from './update/pontos-update.component';
import { PontosDeleteDialogComponent } from './delete/pontos-delete-dialog.component';
import { PontosRoutingModule } from './route/pontos-routing.module';

@NgModule({
  imports: [SharedModule, PontosRoutingModule],
  declarations: [PontosComponent, PontosDetailComponent, PontosUpdateComponent, PontosDeleteDialogComponent],
})
export class PontosModule {}
