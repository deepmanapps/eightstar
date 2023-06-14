import { ILocation } from 'app/entities/location/location.model';
import { IHotel } from 'app/entities/hotel/hotel.model';

export interface IParkingAll {
  id: number;
  name?: string | null;
  location?: Pick<ILocation, 'id'> | null;
  hotel?: IHotel | null;
}

export type NewParkingAll = Omit<IParkingAll, 'id'> & { id: null };
