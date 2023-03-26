import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISpatialUnit, NewSpatialUnit } from '../spatial-unit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISpatialUnit for edit and NewSpatialUnitFormGroupInput for create.
 */
type SpatialUnitFormGroupInput = ISpatialUnit | PartialWithRequiredKeyOf<NewSpatialUnit>;

type SpatialUnitFormDefaults = Pick<NewSpatialUnit, 'id'>;

type SpatialUnitFormGroupContent = {
  id: FormControl<ISpatialUnit['id'] | NewSpatialUnit['id']>;
  parcela: FormControl<ISpatialUnit['parcela']>;
  ficha: FormControl<ISpatialUnit['ficha']>;
};

export type SpatialUnitFormGroup = FormGroup<SpatialUnitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SpatialUnitFormService {
  createSpatialUnitFormGroup(spatialUnit: SpatialUnitFormGroupInput = { id: null }): SpatialUnitFormGroup {
    const spatialUnitRawValue = {
      ...this.getFormDefaults(),
      ...spatialUnit,
    };
    return new FormGroup<SpatialUnitFormGroupContent>({
      id: new FormControl(
        { value: spatialUnitRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      parcela: new FormControl(spatialUnitRawValue.parcela),
      ficha: new FormControl(spatialUnitRawValue.ficha),
    });
  }

  getSpatialUnit(form: SpatialUnitFormGroup): ISpatialUnit | NewSpatialUnit {
    return form.getRawValue() as ISpatialUnit | NewSpatialUnit;
  }

  resetForm(form: SpatialUnitFormGroup, spatialUnit: SpatialUnitFormGroupInput): void {
    const spatialUnitRawValue = { ...this.getFormDefaults(), ...spatialUnit };
    form.reset(
      {
        ...spatialUnitRawValue,
        id: { value: spatialUnitRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SpatialUnitFormDefaults {
    return {
      id: null,
    };
  }
}
