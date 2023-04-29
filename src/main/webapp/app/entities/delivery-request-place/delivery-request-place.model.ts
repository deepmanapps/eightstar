import { IHotel } from 'app/entities/hotel/hotel.model';

export interface IDeliveryRequestPlace {
  id: number;
  name?: string | null;
  hotel?: Pick<IHotel, 'id'> | null;
}

export type NewDeliveryRequestPlace = Omit<IDeliveryRequestPlace, 'id'> & { id: null };
