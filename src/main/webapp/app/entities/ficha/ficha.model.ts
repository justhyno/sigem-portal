import { IProjecto } from 'app/entities/projecto/projecto.model';

export interface IFicha {
  id: number;
  parcela?: string | null;
  processo?: string | null;
  projecto?: Pick<IProjecto, 'id' | 'descricao'> | null;
}

export type NewFicha = Omit<IFicha, 'id'> & { id: null };
