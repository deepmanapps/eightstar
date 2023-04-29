import { ILocation } from 'app/entities/location/location.model';

export interface IHotel {
  id: number;
  hotelId?: number | null;
  name?: string | null;
  description?: string | null;
  adresse?: string | null;
  starsNumber?: number | null;
  emergencyNumber?: number | null;
  location?: Pick<ILocation, 'id'> | null;
}

export type NewHotel = Omit<IHotel, 'id'> & { id: null };
