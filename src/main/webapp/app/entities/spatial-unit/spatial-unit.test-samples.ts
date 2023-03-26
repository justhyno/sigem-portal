import { ISpatialUnit, NewSpatialUnit } from './spatial-unit.model';

export const sampleWithRequiredData: ISpatialUnit = {
  id: 1795,
};

export const sampleWithPartialData: ISpatialUnit = {
  id: 11471,
  parcela: 'generating core sexy',
};

export const sampleWithFullData: ISpatialUnit = {
  id: 36882,
  parcela: 'Inteligente solid Intuitive',
};

export const sampleWithNewData: NewSpatialUnit = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
