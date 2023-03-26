import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProjecto, NewProjecto } from '../projecto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjecto for edit and NewProjectoFormGroupInput for create.
 */
type ProjectoFormGroupInput = IProjecto | PartialWithRequiredKeyOf<NewProjecto>;

type ProjectoFormDefaults = Pick<NewProjecto, 'id'>;

type ProjectoFormGroupContent = {
  id: FormControl<IProjecto['id'] | NewProjecto['id']>;
  nome: FormControl<IProjecto['nome']>;
  zona: FormControl<IProjecto['zona']>;
  descricao: FormControl<IProjecto['descricao']>;
  codigo: FormControl<IProjecto['codigo']>;
};

export type ProjectoFormGroup = FormGroup<ProjectoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectoFormService {
  createProjectoFormGroup(projecto: ProjectoFormGroupInput = { id: null }): ProjectoFormGroup {
    const projectoRawValue = {
      ...this.getFormDefaults(),
      ...projecto,
    };
    return new FormGroup<ProjectoFormGroupContent>({
      id: new FormControl(
        { value: projectoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nome: new FormControl(projectoRawValue.nome),
      zona: new FormControl(projectoRawValue.zona),
      descricao: new FormControl(projectoRawValue.descricao),
      codigo: new FormControl(projectoRawValue.codigo),
    });
  }

  getProjecto(form: ProjectoFormGroup): IProjecto | NewProjecto {
    return form.getRawValue() as IProjecto | NewProjecto;
  }

  resetForm(form: ProjectoFormGroup, projecto: ProjectoFormGroupInput): void {
    const projectoRawValue = { ...this.getFormDefaults(), ...projecto };
    form.reset(
      {
        ...projectoRawValue,
        id: { value: projectoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjectoFormDefaults {
    return {
      id: null,
    };
  }
}
