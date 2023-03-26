import { IFicha } from 'app/entities/ficha/ficha.model';

export interface ISpatialUnit {
  id: number;
  parcela?: string | null;
  ficha?: Pick<IFicha, 'id' | 'processo'> | null;
}

export type NewSpatialUnit = Omit<ISpatialUnit, 'id'> & { id: null };
