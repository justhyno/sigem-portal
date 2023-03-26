import { IPontos, NewPontos } from './pontos.model';

export const sampleWithRequiredData: IPontos = {
  id: 63564,
};

export const sampleWithPartialData: IPontos = {
  id: 99557,
  parcela: 'North',
};

export const sampleWithFullData: IPontos = {
  id: 93589,
  parcela: 'viral neutral hack',
  pointX: 94480,
  pointY: 27539,
  marco: 'Ergonómico',
  zona: 'Inteligente',
};

export const sampleWithNewData: NewPontos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
