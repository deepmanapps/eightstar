export interface IServices {
  id: number;
  nom?: string | null;
  description?: string | null;
  parentService?: IServices | null;
}

export type NewServices = Omit<IServices, 'id'> & { id: null };

export interface IServicesAll {
  id: number;
  nom?: string | null;
  description?: string | null;
  parentService?: IServices | null;
}
export interface IServicesAll extends Array<IServicesAll> {}
