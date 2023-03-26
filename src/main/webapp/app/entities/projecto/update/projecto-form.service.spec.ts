import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../projecto.test-samples';

import { ProjectoFormService } from './projecto-form.service';

describe('Projecto Form Service', () => {
  let service: ProjectoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectoFormService);
  });

  describe('Service methods', () => {
    describe('createProjectoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            zona: expect.any(Object),
            descricao: expect.any(Object),
            codigo: expect.any(Object),
          })
        );
      });

      it('passing IProjecto should create a new form with FormGroup', () => {
        const formGroup = service.createProjectoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            zona: expect.any(Object),
            descricao: expect.any(Object),
            codigo: expect.any(Object),
          })
        );
      });
    });

    describe('getProjecto', () => {
      it('should return NewProjecto for default Projecto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProjectoFormGroup(sampleWithNewData);

        const projecto = service.getProjecto(formGroup) as any;

        expect(projecto).toMatchObject(sampleWithNewData);
      });

      it('should return NewProjecto for empty Projecto initial value', () => {
        const formGroup = service.createProjectoFormGroup();

        const projecto = service.getProjecto(formGroup) as any;

        expect(projecto).toMatchObject({});
      });

      it('should return IProjecto', () => {
        const formGroup = service.createProjectoFormGroup(sampleWithRequiredData);

        const projecto = service.getProjecto(formGroup) as any;

        expect(projecto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProjecto should not enable id FormControl', () => {
        const formGroup = service.createProjectoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProjecto should disable id FormControl', () => {
        const formGroup = service.createProjectoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
