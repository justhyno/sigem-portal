import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpatialUnit } from '../spatial-unit.model';
import { SpatialUnitService } from '../service/spatial-unit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './spatial-unit-delete-dialog.component.html',
})
export class SpatialUnitDeleteDialogComponent {
  spatialUnit?: ISpatialUnit;

  constructor(protected spatialUnitService: SpatialUnitService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.spatialUnitService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
