import dayjs from 'dayjs/esm';

import { IAlfa, NewAlfa } from './alfa.model';

export const sampleWithRequiredData: IAlfa = {
  id: 57239,
};

export const sampleWithPartialData: IAlfa = {
  id: 20177,
};

export const sampleWithFullData: IAlfa = {
  id: 28903,
  parcela: 'Licenciado Rest Automated',
  dataLevantamento: dayjs('2023-03-25'),
};

export const sampleWithNewData: NewAlfa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
