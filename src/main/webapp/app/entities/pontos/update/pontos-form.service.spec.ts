import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pontos.test-samples';

import { PontosFormService } from './pontos-form.service';

describe('Pontos Form Service', () => {
  let service: PontosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PontosFormService);
  });

  describe('Service methods', () => {
    describe('createPontosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPontosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            pointX: expect.any(Object),
            pointY: expect.any(Object),
            marco: expect.any(Object),
            zona: expect.any(Object),
            spatialUnit: expect.any(Object),
          })
        );
      });

      it('passing IPontos should create a new form with FormGroup', () => {
        const formGroup = service.createPontosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            pointX: expect.any(Object),
            pointY: expect.any(Object),
            marco: expect.any(Object),
            zona: expect.any(Object),
            spatialUnit: expect.any(Object),
          })
        );
      });
    });

    describe('getPontos', () => {
      it('should return NewPontos for default Pontos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPontosFormGroup(sampleWithNewData);

        const pontos = service.getPontos(formGroup) as any;

        expect(pontos).toMatchObject(sampleWithNewData);
      });

      it('should return NewPontos for empty Pontos initial value', () => {
        const formGroup = service.createPontosFormGroup();

        const pontos = service.getPontos(formGroup) as any;

        expect(pontos).toMatchObject({});
      });

      it('should return IPontos', () => {
        const formGroup = service.createPontosFormGroup(sampleWithRequiredData);

        const pontos = service.getPontos(formGroup) as any;

        expect(pontos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPontos should not enable id FormControl', () => {
        const formGroup = service.createPontosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPontos should disable id FormControl', () => {
        const formGroup = service.createPontosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
