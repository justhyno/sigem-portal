import { IProjecto, NewProjecto } from './projecto.model';

export const sampleWithRequiredData: IProjecto = {
  id: 72055,
};

export const sampleWithPartialData: IProjecto = {
  id: 23344,
};

export const sampleWithFullData: IProjecto = {
  id: 68628,
  nome: 'multi-byte Minnesota database',
  zona: 'withdrawal',
  descricao: 'applications',
};

export const sampleWithNewData: NewProjecto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
