import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../spatial-unit.test-samples';

import { SpatialUnitFormService } from './spatial-unit-form.service';

describe('SpatialUnit Form Service', () => {
  let service: SpatialUnitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpatialUnitFormService);
  });

  describe('Service methods', () => {
    describe('createSpatialUnitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSpatialUnitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            ficha: expect.any(Object),
          })
        );
      });

      it('passing ISpatialUnit should create a new form with FormGroup', () => {
        const formGroup = service.createSpatialUnitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            ficha: expect.any(Object),
          })
        );
      });
    });

    describe('getSpatialUnit', () => {
      it('should return NewSpatialUnit for default SpatialUnit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSpatialUnitFormGroup(sampleWithNewData);

        const spatialUnit = service.getSpatialUnit(formGroup) as any;

        expect(spatialUnit).toMatchObject(sampleWithNewData);
      });

      it('should return NewSpatialUnit for empty SpatialUnit initial value', () => {
        const formGroup = service.createSpatialUnitFormGroup();

        const spatialUnit = service.getSpatialUnit(formGroup) as any;

        expect(spatialUnit).toMatchObject({});
      });

      it('should return ISpatialUnit', () => {
        const formGroup = service.createSpatialUnitFormGroup(sampleWithRequiredData);

        const spatialUnit = service.getSpatialUnit(formGroup) as any;

        expect(spatialUnit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISpatialUnit should not enable id FormControl', () => {
        const formGroup = service.createSpatialUnitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSpatialUnit should disable id FormControl', () => {
        const formGroup = service.createSpatialUnitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
