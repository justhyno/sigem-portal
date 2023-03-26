import dayjs from 'dayjs/esm';
import { IFicha } from 'app/entities/ficha/ficha.model';

export interface IAlfa {
  id: number;
  parcela?: string | null;
  dataLevantamento?: dayjs.Dayjs | null;
  ficha?: Pick<IFicha, 'id' | 'processo'> | null;
}

export type NewAlfa = Omit<IAlfa, 'id'> & { id: null };
