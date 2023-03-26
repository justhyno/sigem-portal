import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPontos, NewPontos } from '../pontos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPontos for edit and NewPontosFormGroupInput for create.
 */
type PontosFormGroupInput = IPontos | PartialWithRequiredKeyOf<NewPontos>;

type PontosFormDefaults = Pick<NewPontos, 'id'>;

type PontosFormGroupContent = {
  id: FormControl<IPontos['id'] | NewPontos['id']>;
  parcela: FormControl<IPontos['parcela']>;
  pointX: FormControl<IPontos['pointX']>;
  pointY: FormControl<IPontos['pointY']>;
  marco: FormControl<IPontos['marco']>;
  zona: FormControl<IPontos['zona']>;
  spatialUnit: FormControl<IPontos['spatialUnit']>;
};

export type PontosFormGroup = FormGroup<PontosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PontosFormService {
  createPontosFormGroup(pontos: PontosFormGroupInput = { id: null }): PontosFormGroup {
    const pontosRawValue = {
      ...this.getFormDefaults(),
      ...pontos,
    };
    return new FormGroup<PontosFormGroupContent>({
      id: new FormControl(
        { value: pontosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      parcela: new FormControl(pontosRawValue.parcela),
      pointX: new FormControl(pontosRawValue.pointX),
      pointY: new FormControl(pontosRawValue.pointY),
      marco: new FormControl(pontosRawValue.marco),
      zona: new FormControl(pontosRawValue.zona),
      spatialUnit: new FormControl(pontosRawValue.spatialUnit),
    });
  }

  getPontos(form: PontosFormGroup): IPontos | NewPontos {
    return form.getRawValue() as IPontos | NewPontos;
  }

  resetForm(form: PontosFormGroup, pontos: PontosFormGroupInput): void {
    const pontosRawValue = { ...this.getFormDefaults(), ...pontos };
    form.reset(
      {
        ...pontosRawValue,
        id: { value: pontosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PontosFormDefaults {
    return {
      id: null,
    };
  }
}
