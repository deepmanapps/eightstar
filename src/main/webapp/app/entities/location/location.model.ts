export interface ILocation {
  id: number;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewLocation = Omit<ILocation, 'id'> & { id: null };
