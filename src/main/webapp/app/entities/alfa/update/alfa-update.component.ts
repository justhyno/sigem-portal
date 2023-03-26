import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AlfaFormService, AlfaFormGroup } from './alfa-form.service';
import { IAlfa } from '../alfa.model';
import { AlfaService } from '../service/alfa.service';
import { IFicha } from 'app/entities/ficha/ficha.model';
import { FichaService } from 'app/entities/ficha/service/ficha.service';

@Component({
  selector: 'jhi-alfa-update',
  templateUrl: './alfa-update.component.html',
})
export class AlfaUpdateComponent implements OnInit {
  isSaving = false;
  alfa: IAlfa | null = null;

  fichasSharedCollection: IFicha[] = [];

  editForm: AlfaFormGroup = this.alfaFormService.createAlfaFormGroup();

  constructor(
    protected alfaService: AlfaService,
    protected alfaFormService: AlfaFormService,
    protected fichaService: FichaService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFicha = (o1: IFicha | null, o2: IFicha | null): boolean => this.fichaService.compareFicha(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alfa }) => {
      this.alfa = alfa;
      if (alfa) {
        this.updateForm(alfa);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alfa = this.alfaFormService.getAlfa(this.editForm);
    if (alfa.id !== null) {
      this.subscribeToSaveResponse(this.alfaService.update(alfa));
    } else {
      this.subscribeToSaveResponse(this.alfaService.create(alfa));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlfa>>): void {
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

  protected updateForm(alfa: IAlfa): void {
    this.alfa = alfa;
    this.alfaFormService.resetForm(this.editForm, alfa);

    this.fichasSharedCollection = this.fichaService.addFichaToCollectionIfMissing<IFicha>(this.fichasSharedCollection, alfa.ficha);
  }

  protected loadRelationshipsOptions(): void {
    this.fichaService
      .query()
      .pipe(map((res: HttpResponse<IFicha[]>) => res.body ?? []))
      .pipe(map((fichas: IFicha[]) => this.fichaService.addFichaToCollectionIfMissing<IFicha>(fichas, this.alfa?.ficha)))
      .subscribe((fichas: IFicha[]) => (this.fichasSharedCollection = fichas));
  }
}
