import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../alfa.test-samples';

import { AlfaFormService } from './alfa-form.service';

describe('Alfa Form Service', () => {
  let service: AlfaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlfaFormService);
  });

  describe('Service methods', () => {
    describe('createAlfaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAlfaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            dataLevantamento: expect.any(Object),
            ficha: expect.any(Object),
          })
        );
      });

      it('passing IAlfa should create a new form with FormGroup', () => {
        const formGroup = service.createAlfaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parcela: expect.any(Object),
            dataLevantamento: expect.any(Object),
            ficha: expect.any(Object),
          })
        );
      });
    });

    describe('getAlfa', () => {
      it('should return NewAlfa for default Alfa initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAlfaFormGroup(sampleWithNewData);

        const alfa = service.getAlfa(formGroup) as any;

        expect(alfa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAlfa for empty Alfa initial value', () => {
        const formGroup = service.createAlfaFormGroup();

        const alfa = service.getAlfa(formGroup) as any;

        expect(alfa).toMatchObject({});
      });

      it('should return IAlfa', () => {
        const formGroup = service.createAlfaFormGroup(sampleWithRequiredData);

        const alfa = service.getAlfa(formGroup) as any;

        expect(alfa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAlfa should not enable id FormControl', () => {
        const formGroup = service.createAlfaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAlfa should disable id FormControl', () => {
        const formGroup = service.createAlfaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
