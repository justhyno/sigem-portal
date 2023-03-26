import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAlfa, NewAlfa } from '../alfa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAlfa for edit and NewAlfaFormGroupInput for create.
 */
type AlfaFormGroupInput = IAlfa | PartialWithRequiredKeyOf<NewAlfa>;

type AlfaFormDefaults = Pick<NewAlfa, 'id'>;

type AlfaFormGroupContent = {
  id: FormControl<IAlfa['id'] | NewAlfa['id']>;
  parcela: FormControl<IAlfa['parcela']>;
  dataLevantamento: FormControl<IAlfa['dataLevantamento']>;
  ficha: FormControl<IAlfa['ficha']>;
};

export type AlfaFormGroup = FormGroup<AlfaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AlfaFormService {
  createAlfaFormGroup(alfa: AlfaFormGroupInput = { id: null }): AlfaFormGroup {
    const alfaRawValue = {
      ...this.getFormDefaults(),
      ...alfa,
    };
    return new FormGroup<AlfaFormGroupContent>({
      id: new FormControl(
        { value: alfaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      parcela: new FormControl(alfaRawValue.parcela),
      dataLevantamento: new FormControl(alfaRawValue.dataLevantamento),
      ficha: new FormControl(alfaRawValue.ficha),
    });
  }

  getAlfa(form: AlfaFormGroup): IAlfa | NewAlfa {
    return form.getRawValue() as IAlfa | NewAlfa;
  }

  resetForm(form: AlfaFormGroup, alfa: AlfaFormGroupInput): void {
    const alfaRawValue = { ...this.getFormDefaults(), ...alfa };
    form.reset(
      {
        ...alfaRawValue,
        id: { value: alfaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AlfaFormDefaults {
    return {
      id: null,
    };
  }
}
