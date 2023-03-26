import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ficha.test-samples';

import { FichaFormService } from './ficha-form.service';

describe('Ficha Form Service', () => {
  let service: FichaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FichaFormService);
  });

  describe('Service methods', () => {
    describe('createFichaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFichaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            processo: expect.any(Object),
            projecto: expect.any(Object),
          })
        );
      });

      it('passing IFicha should create a new form with FormGroup', () => {
        const formGroup = service.createFichaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            processo: expect.any(Object),
            projecto: expect.any(Object),
          })
        );
      });
    });

    describe('getFicha', () => {
      it('should return NewFicha for default Ficha initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFichaFormGroup(sampleWithNewData);

        const ficha = service.getFicha(formGroup) as any;

        expect(ficha).toMatchObject(sampleWithNewData);
      });

      it('should return NewFicha for empty Ficha initial value', () => {
        const formGroup = service.createFichaFormGroup();

        const ficha = service.getFicha(formGroup) as any;

        expect(ficha).toMatchObject({});
      });

      it('should return IFicha', () => {
        const formGroup = service.createFichaFormGroup(sampleWithRequiredData);

        const ficha = service.getFicha(formGroup) as any;

        expect(ficha).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFicha should not enable id FormControl', () => {
        const formGroup = service.createFichaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFicha should disable id FormControl', () => {
        const formGroup = service.createFichaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
