import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FichaFormService, FichaFormGroup } from './ficha-form.service';
import { IFicha } from '../ficha.model';
import { FichaService } from '../service/ficha.service';
import { IProjecto } from 'app/entities/projecto/projecto.model';
import { ProjectoService } from 'app/entities/projecto/service/projecto.service';

@Component({
  selector: 'jhi-ficha-update',
  templateUrl: './ficha-update.component.html',
})
export class FichaUpdateComponent implements OnInit {
  isSaving = false;
  ficha: IFicha | null = null;

  projectosSharedCollection: IProjecto[] = [];

  editForm: FichaFormGroup = this.fichaFormService.createFichaFormGroup();

  constructor(
    protected fichaService: FichaService,
    protected fichaFormService: FichaFormService,
    protected projectoService: ProjectoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProjecto = (o1: IProjecto | null, o2: IProjecto | null): boolean => this.projectoService.compareProjecto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ficha }) => {
      this.ficha = ficha;
      if (ficha) {
        this.updateForm(ficha);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ficha = this.fichaFormService.getFicha(this.editForm);
    if (ficha.id !== null) {
      this.subscribeToSaveResponse(this.fichaService.update(ficha));
    } else {
      this.subscribeToSaveResponse(this.fichaService.create(ficha));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFicha>>): void {
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

  protected updateForm(ficha: IFicha): void {
    this.ficha = ficha;
    this.fichaFormService.resetForm(this.editForm, ficha);

    this.projectosSharedCollection = this.projectoService.addProjectoToCollectionIfMissing<IProjecto>(
      this.projectosSharedCollection,
      ficha.projecto
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectoService
      .query()
      .pipe(map((res: HttpResponse<IProjecto[]>) => res.body ?? []))
      .pipe(
        map((projectos: IProjecto[]) => this.projectoService.addProjectoToCollectionIfMissing<IProjecto>(projectos, this.ficha?.projecto))
      )
      .subscribe((projectos: IProjecto[]) => (this.projectosSharedCollection = projectos));
  }
}
