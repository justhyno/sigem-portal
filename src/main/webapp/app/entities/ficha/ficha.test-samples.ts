import { IFicha, NewFicha } from './ficha.model';

export const sampleWithRequiredData: IFicha = {
  id: 10220,
};

export const sampleWithPartialData: IFicha = {
  id: 10992,
  parcela: 'bypass Aço',
};

export const sampleWithFullData: IFicha = {
  id: 2408,
  parcela: 'transmitter',
  processo: 'solution up',
};

export const sampleWithNewData: NewFicha = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
