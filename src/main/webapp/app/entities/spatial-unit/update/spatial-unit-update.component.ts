import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SpatialUnitFormService, SpatialUnitFormGroup } from './spatial-unit-form.service';
import { ISpatialUnit } from '../spatial-unit.model';
import { SpatialUnitService } from '../service/spatial-unit.service';
import { IFicha } from 'app/entities/ficha/ficha.model';
import { FichaService } from 'app/entities/ficha/service/ficha.service';

@Component({
  selector: 'jhi-spatial-unit-update',
  templateUrl: './spatial-unit-update.component.html',
})
export class SpatialUnitUpdateComponent implements OnInit {
  isSaving = false;
  spatialUnit: ISpatialUnit | null = null;

  fichasSharedCollection: IFicha[] = [];

  editForm: SpatialUnitFormGroup = this.spatialUnitFormService.createSpatialUnitFormGroup();

  constructor(
    protected spatialUnitService: SpatialUnitService,
    protected spatialUnitFormService: SpatialUnitFormService,
    protected fichaService: FichaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFicha = (o1: IFicha | null, o2: IFicha | null): boolean => this.fichaService.compareFicha(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spatialUnit }) => {
      this.spatialUnit = spatialUnit;
      if (spatialUnit) {
        this.updateForm(spatialUnit);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const spatialUnit = this.spatialUnitFormService.getSpatialUnit(this.editForm);
    if (spatialUnit.id !== null) {
      this.subscribeToSaveResponse(this.spatialUnitService.update(spatialUnit));
    } else {
      this.subscribeToSaveResponse(this.spatialUnitService.create(spatialUnit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpatialUnit>>): void {
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

  protected updateForm(spatialUnit: ISpatialUnit): void {
    this.spatialUnit = spatialUnit;
    this.spatialUnitFormService.resetForm(this.editForm, spatialUnit);

    this.fichasSharedCollection = this.fichaService.addFichaToCollectionIfMissing<IFicha>(this.fichasSharedCollection, spatialUnit.ficha);
  }

  protected loadRelationshipsOptions(): void {
    this.fichaService
      .query()
      .pipe(map((res: HttpResponse<IFicha[]>) => res.body ?? []))
      .pipe(map((fichas: IFicha[]) => this.fichaService.addFichaToCollectionIfMissing<IFicha>(fichas, this.spatialUnit?.ficha)))
      .subscribe((fichas: IFicha[]) => (this.fichasSharedCollection = fichas));
  }
}
