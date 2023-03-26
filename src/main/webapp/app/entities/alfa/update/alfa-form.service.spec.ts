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
            tipoTitular: expect.any(Object),
            nomeTitular: expect.any(Object),
            estadoSocial: expect.any(Object),
            dataNascimento: expect.any(Object),
            sexo: expect.any(Object),
            documento: expect.any(Object),
            numeroDocumento: expect.any(Object),
            datEmissao: expect.any(Object),
            localEmissao: expect.any(Object),
            contactoPrincipal: expect.any(Object),
            contactoAlternativo: expect.any(Object),
            estadoCivil: expect.any(Object),
            nomeConjugue: expect.any(Object),
            distritoMunicipal: expect.any(Object),
            bairro: expect.any(Object),
            quatreirao: expect.any(Object),
            talhao: expect.any(Object),
            celula: expect.any(Object),
            bloco: expect.any(Object),
            avenida: expect.any(Object),
            numeroPolicia: expect.any(Object),
            usoActual: expect.any(Object),
            formaUso: expect.any(Object),
            formaObtencao: expect.any(Object),
            tipo: expect.any(Object),
            anoOcupacao: expect.any(Object),
            tipoAcesso: expect.any(Object),
            conflito: expect.any(Object),
            detalhesConflito: expect.any(Object),
            construcaoPrecaria: expect.any(Object),
            pisosAcimaSoleira: expect.any(Object),
            pisosAbaixoSoleira: expect.any(Object),
            materialConstrucaoBarrote: expect.any(Object),
            materialConstrucaoIBR: expect.any(Object),
            materialConstrucaoPranchas: expect.any(Object),
            materialConstrucaoPau: expect.any(Object),
            materialConstrucaoCanico: expect.any(Object),
            materialConstrucaoCimento: expect.any(Object),
            ocupacao: expect.any(Object),
            TipoContrucao: expect.any(Object),
            detalhesTipoContrucao: expect.any(Object),
            infraestruturaExistente: expect.any(Object),
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
            tipoTitular: expect.any(Object),
            nomeTitular: expect.any(Object),
            estadoSocial: expect.any(Object),
            dataNascimento: expect.any(Object),
            sexo: expect.any(Object),
            documento: expect.any(Object),
            numeroDocumento: expect.any(Object),
            datEmissao: expect.any(Object),
            localEmissao: expect.any(Object),
            contactoPrincipal: expect.any(Object),
            contactoAlternativo: expect.any(Object),
            estadoCivil: expect.any(Object),
            nomeConjugue: expect.any(Object),
            distritoMunicipal: expect.any(Object),
            bairro: expect.any(Object),
            quatreirao: expect.any(Object),
            talhao: expect.any(Object),
            celula: expect.any(Object),
            bloco: expect.any(Object),
            avenida: expect.any(Object),
            numeroPolicia: expect.any(Object),
            usoActual: expect.any(Object),
            formaUso: expect.any(Object),
            formaObtencao: expect.any(Object),
            tipo: expect.any(Object),
            anoOcupacao: expect.any(Object),
            tipoAcesso: expect.any(Object),
            conflito: expect.any(Object),
            detalhesConflito: expect.any(Object),
            construcaoPrecaria: expect.any(Object),
            pisosAcimaSoleira: expect.any(Object),
            pisosAbaixoSoleira: expect.any(Object),
            materialConstrucaoBarrote: expect.any(Object),
            materialConstrucaoIBR: expect.any(Object),
            materialConstrucaoPranchas: expect.any(Object),
            materialConstrucaoPau: expect.any(Object),
            materialConstrucaoCanico: expect.any(Object),
            materialConstrucaoCimento: expect.any(Object),
            ocupacao: expect.any(Object),
            TipoContrucao: expect.any(Object),
            detalhesTipoContrucao: expect.any(Object),
            infraestruturaExistente: expect.any(Object),
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
