import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFicha, NewFicha } from '../ficha.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFicha for edit and NewFichaFormGroupInput for create.
 */
type FichaFormGroupInput = IFicha | PartialWithRequiredKeyOf<NewFicha>;

type FichaFormDefaults = Pick<NewFicha, 'id'>;

type FichaFormGroupContent = {
  id: FormControl<IFicha['id'] | NewFicha['id']>;
  parcela: FormControl<IFicha['parcela']>;
  processo: FormControl<IFicha['processo']>;
  projecto: FormControl<IFicha['projecto']>;
};

export type FichaFormGroup = FormGroup<FichaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FichaFormService {
  createFichaFormGroup(ficha: FichaFormGroupInput = { id: null }): FichaFormGroup {
    const fichaRawValue = {
      ...this.getFormDefaults(),
      ...ficha,
    };
    return new FormGroup<FichaFormGroupContent>({
      id: new FormControl(
        { value: fichaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      parcela: new FormControl(fichaRawValue.parcela),
      processo: new FormControl(fichaRawValue.processo),
      projecto: new FormControl(fichaRawValue.projecto, {
        validators: [Validators.required],
      }),
    });
  }

  getFicha(form: FichaFormGroup): IFicha | NewFicha {
    return form.getRawValue() as IFicha | NewFicha;
  }

  resetForm(form: FichaFormGroup, ficha: FichaFormGroupInput): void {
    const fichaRawValue = { ...this.getFormDefaults(), ...ficha };
    form.reset(
      {
        ...fichaRawValue,
        id: { value: fichaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FichaFormDefaults {
    return {
      id: null,
    };
  }
}
