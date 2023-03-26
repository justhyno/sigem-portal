import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpatialUnit } from '../spatial-unit.model';

@Component({
  selector: 'jhi-spatial-unit-detail',
  templateUrl: './spatial-unit-detail.component.html',
})
export class SpatialUnitDetailComponent implements OnInit {
  spatialUnit: ISpatialUnit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spatialUnit }) => {
      this.spatialUnit = spatialUnit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
