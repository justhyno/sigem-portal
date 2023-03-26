export interface IProjecto {
  id: number;
  nome?: string | null;
  zona?: string | null;
  descricao?: string | null;
}

export type NewProjecto = Omit<IProjecto, 'id'> & { id: null };
