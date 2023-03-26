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

type AlfaFormDefaults = Pick<
  NewAlfa,
  | 'id'
  | 'conflito'
  | 'detalhesConflito'
  | 'construcaoPrecaria'
  | 'materialConstrucaoBarrote'
  | 'materialConstrucaoIBR'
  | 'materialConstrucaoPranchas'
  | 'materialConstrucaoPau'
  | 'materialConstrucaoCanico'
  | 'materialConstrucaoCimento'
>;

type AlfaFormGroupContent = {
  id: FormControl<IAlfa['id'] | NewAlfa['id']>;
  parcela: FormControl<IAlfa['parcela']>;
  tipoTitular: FormControl<IAlfa['tipoTitular']>;
  nomeTitular: FormControl<IAlfa['nomeTitular']>;
  estadoSocial: FormControl<IAlfa['estadoSocial']>;
  dataNascimento: FormControl<IAlfa['dataNascimento']>;
  sexo: FormControl<IAlfa['sexo']>;
  documento: FormControl<IAlfa['documento']>;
  numeroDocumento: FormControl<IAlfa['numeroDocumento']>;
  datEmissao: FormControl<IAlfa['datEmissao']>;
  localEmissao: FormControl<IAlfa['localEmissao']>;
  contactoPrincipal: FormControl<IAlfa['contactoPrincipal']>;
  contactoAlternativo: FormControl<IAlfa['contactoAlternativo']>;
  estadoCivil: FormControl<IAlfa['estadoCivil']>;
  nomeConjugue: FormControl<IAlfa['nomeConjugue']>;
  distritoMunicipal: FormControl<IAlfa['distritoMunicipal']>;
  bairro: FormControl<IAlfa['bairro']>;
  quatreirao: FormControl<IAlfa['quatreirao']>;
  talhao: FormControl<IAlfa['talhao']>;
  celula: FormControl<IAlfa['celula']>;
  bloco: FormControl<IAlfa['bloco']>;
  avenida: FormControl<IAlfa['avenida']>;
  numeroPolicia: FormControl<IAlfa['numeroPolicia']>;
  usoActual: FormControl<IAlfa['usoActual']>;
  formaUso: FormControl<IAlfa['formaUso']>;
  formaObtencao: FormControl<IAlfa['formaObtencao']>;
  tipo: FormControl<IAlfa['tipo']>;
  anoOcupacao: FormControl<IAlfa['anoOcupacao']>;
  tipoAcesso: FormControl<IAlfa['tipoAcesso']>;
  conflito: FormControl<IAlfa['conflito']>;
  detalhesConflito: FormControl<IAlfa['detalhesConflito']>;
  construcaoPrecaria: FormControl<IAlfa['construcaoPrecaria']>;
  pisosAcimaSoleira: FormControl<IAlfa['pisosAcimaSoleira']>;
  pisosAbaixoSoleira: FormControl<IAlfa['pisosAbaixoSoleira']>;
  materialConstrucaoBarrote: FormControl<IAlfa['materialConstrucaoBarrote']>;
  materialConstrucaoIBR: FormControl<IAlfa['materialConstrucaoIBR']>;
  materialConstrucaoPranchas: FormControl<IAlfa['materialConstrucaoPranchas']>;
  materialConstrucaoPau: FormControl<IAlfa['materialConstrucaoPau']>;
  materialConstrucaoCanico: FormControl<IAlfa['materialConstrucaoCanico']>;
  materialConstrucaoCimento: FormControl<IAlfa['materialConstrucaoCimento']>;
  ocupacao: FormControl<IAlfa['ocupacao']>;
  TipoContrucao: FormControl<IAlfa['TipoContrucao']>;
  detalhesTipoContrucao: FormControl<IAlfa['detalhesTipoContrucao']>;
  infraestruturaExistente: FormControl<IAlfa['infraestruturaExistente']>;
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
      tipoTitular: new FormControl(alfaRawValue.tipoTitular),
      nomeTitular: new FormControl(alfaRawValue.nomeTitular),
      estadoSocial: new FormControl(alfaRawValue.estadoSocial),
      dataNascimento: new FormControl(alfaRawValue.dataNascimento),
      sexo: new FormControl(alfaRawValue.sexo),
      documento: new FormControl(alfaRawValue.documento),
      numeroDocumento: new FormControl(alfaRawValue.numeroDocumento),
      datEmissao: new FormControl(alfaRawValue.datEmissao),
      localEmissao: new FormControl(alfaRawValue.localEmissao),
      contactoPrincipal: new FormControl(alfaRawValue.contactoPrincipal),
      contactoAlternativo: new FormControl(alfaRawValue.contactoAlternativo),
      estadoCivil: new FormControl(alfaRawValue.estadoCivil),
      nomeConjugue: new FormControl(alfaRawValue.nomeConjugue),
      distritoMunicipal: new FormControl(alfaRawValue.distritoMunicipal),
      bairro: new FormControl(alfaRawValue.bairro),
      quatreirao: new FormControl(alfaRawValue.quatreirao),
      talhao: new FormControl(alfaRawValue.talhao),
      celula: new FormControl(alfaRawValue.celula),
      bloco: new FormControl(alfaRawValue.bloco),
      avenida: new FormControl(alfaRawValue.avenida),
      numeroPolicia: new FormControl(alfaRawValue.numeroPolicia),
      usoActual: new FormControl(alfaRawValue.usoActual),
      formaUso: new FormControl(alfaRawValue.formaUso),
      formaObtencao: new FormControl(alfaRawValue.formaObtencao),
      tipo: new FormControl(alfaRawValue.tipo),
      anoOcupacao: new FormControl(alfaRawValue.anoOcupacao),
      tipoAcesso: new FormControl(alfaRawValue.tipoAcesso),
      conflito: new FormControl(alfaRawValue.conflito),
      detalhesConflito: new FormControl(alfaRawValue.detalhesConflito),
      construcaoPrecaria: new FormControl(alfaRawValue.construcaoPrecaria),
      pisosAcimaSoleira: new FormControl(alfaRawValue.pisosAcimaSoleira),
      pisosAbaixoSoleira: new FormControl(alfaRawValue.pisosAbaixoSoleira),
      materialConstrucaoBarrote: new FormControl(alfaRawValue.materialConstrucaoBarrote),
      materialConstrucaoIBR: new FormControl(alfaRawValue.materialConstrucaoIBR),
      materialConstrucaoPranchas: new FormControl(alfaRawValue.materialConstrucaoPranchas),
      materialConstrucaoPau: new FormControl(alfaRawValue.materialConstrucaoPau),
      materialConstrucaoCanico: new FormControl(alfaRawValue.materialConstrucaoCanico),
      materialConstrucaoCimento: new FormControl(alfaRawValue.materialConstrucaoCimento),
      ocupacao: new FormControl(alfaRawValue.ocupacao),
      TipoContrucao: new FormControl(alfaRawValue.TipoContrucao),
      detalhesTipoContrucao: new FormControl(alfaRawValue.detalhesTipoContrucao),
      infraestruturaExistente: new FormControl(alfaRawValue.infraestruturaExistente),
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
      conflito: false,
      detalhesConflito: false,
      construcaoPrecaria: false,
      materialConstrucaoBarrote: false,
      materialConstrucaoIBR: false,
      materialConstrucaoPranchas: false,
      materialConstrucaoPau: false,
      materialConstrucaoCanico: false,
      materialConstrucaoCimento: false,
    };
  }
}
