import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProjectoFormService, ProjectoFormGroup } from './projecto-form.service';
import { IProjecto } from '../projecto.model';
import { ProjectoService } from '../service/projecto.service';

@Component({
  selector: 'jhi-projecto-update',
  templateUrl: './projecto-update.component.html',
})
export class ProjectoUpdateComponent implements OnInit {
  isSaving = false;
  projecto: IProjecto | null = null;

  editForm: ProjectoFormGroup = this.projectoFormService.createProjectoFormGroup();

  constructor(
    protected projectoService: ProjectoService,
    protected projectoFormService: ProjectoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projecto }) => {
      this.projecto = projecto;
      if (projecto) {
        this.updateForm(projecto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projecto = this.projectoFormService.getProjecto(this.editForm);
    if (projecto.id !== null) {
      this.subscribeToSaveResponse(this.projectoService.update(projecto));
    } else {
      this.subscribeToSaveResponse(this.projectoService.create(projecto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjecto>>): void {
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

  protected updateForm(projecto: IProjecto): void {
    this.projecto = projecto;
    this.projectoFormService.resetForm(this.editForm, projecto);
  }
}
