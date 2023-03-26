import { ISpatialUnit } from 'app/entities/spatial-unit/spatial-unit.model';

export interface IPontos {
  id: number;
  parcela?: string | null;
  pointX?: number | null;
  pointY?: number | null;
  marco?: string | null;
  zona?: string | null;
  spatialUnit?: Pick<ISpatialUnit, 'id'> | null;
}

export type NewPontos = Omit<IPontos, 'id'> & { id: null };
