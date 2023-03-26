import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PontosFormService, PontosFormGroup } from './pontos-form.service';
import { IPontos } from '../pontos.model';
import { PontosService } from '../service/pontos.service';
import { ISpatialUnit } from 'app/entities/spatial-unit/spatial-unit.model';
import { SpatialUnitService } from 'app/entities/spatial-unit/service/spatial-unit.service';

@Component({
  selector: 'jhi-pontos-update',
  templateUrl: './pontos-update.component.html',
})
export class PontosUpdateComponent implements OnInit {
  isSaving = false;
  pontos: IPontos | null = null;

  spatialUnitsSharedCollection: ISpatialUnit[] = [];

  editForm: PontosFormGroup = this.pontosFormService.createPontosFormGroup();

  constructor(
    protected pontosService: PontosService,
    protected pontosFormService: PontosFormService,
    protected spatialUnitService: SpatialUnitService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSpatialUnit = (o1: ISpatialUnit | null, o2: ISpatialUnit | null): boolean => this.spatialUnitService.compareSpatialUnit(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pontos }) => {
      this.pontos = pontos;
      if (pontos) {
        this.updateForm(pontos);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pontos = this.pontosFormService.getPontos(this.editForm);
    if (pontos.id !== null) {
      this.subscribeToSaveResponse(this.pontosService.update(pontos));
    } else {
      this.subscribeToSaveResponse(this.pontosService.create(pontos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPontos>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pontos: IPontos): void {
    this.pontos = pontos;
    this.pontosFormService.resetForm(this.editForm, pontos);

    this.spatialUnitsSharedCollection = this.spatialUnitService.addSpatialUnitToCollectionIfMissing<ISpatialUnit>(
      this.spatialUnitsSharedCollection,
      pontos.spatialUnit
    );
  }

  protected loadRelationshipsOptions(): void {
    this.spatialUnitService
      .query()
      .pipe(map((res: HttpResponse<ISpatialUnit[]>) => res.body ?? []))
      .pipe(
        map((spatialUnits: ISpatialUnit[]) =>
          this.spatialUnitService.addSpatialUnitToCollectionIfMissing<ISpatialUnit>(spatialUnits, this.pontos?.spatialUnit)
        )
      )
      .subscribe((spatialUnits: ISpatialUnit[]) => (this.spatialUnitsSharedCollection = spatialUnits));
  }
}
