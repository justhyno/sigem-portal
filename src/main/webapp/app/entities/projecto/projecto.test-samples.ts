import { IProjecto, NewProjecto } from './projecto.model';

export const sampleWithRequiredData: IProjecto = {
  id: 72055,
};

export const sampleWithPartialData: IProjecto = {
  id: 68628,
};

export const sampleWithFullData: IProjecto = {
  id: 68154,
  nome: 'Market vortals Proactive',
  zona: '24/365 applications',
  descricao: 'Teclado',
  codigo: 35133,
};

export const sampleWithNewData: NewProjecto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
