import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AlfaComponent } from './list/alfa.component';
import { AlfaDetailComponent } from './detail/alfa-detail.component';
import { AlfaUpdateComponent } from './update/alfa-update.component';
import { AlfaDeleteDialogComponent } from './delete/alfa-delete-dialog.component';
import { AlfaRoutingModule } from './route/alfa-routing.module';

@NgModule({
  imports: [SharedModule, AlfaRoutingModule],
  declarations: [AlfaComponent, AlfaDetailComponent, AlfaUpdateComponent, AlfaDeleteDialogComponent],
})
export class AlfaModule {}
