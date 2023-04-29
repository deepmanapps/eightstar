import { IHotel } from 'app/entities/hotel/hotel.model';
import { IServices } from 'app/entities/services/services.model';

export interface IHotelServices {
  id: number;
  active?: boolean | null;
  forGuest?: boolean | null;
  servicePrice?: number | null;
  hotel?: Pick<IHotel, 'id'> | null;
  services?: Pick<IServices, 'id'> | null;
}

export type NewHotelServices = Omit<IHotelServices, 'id'> & { id: null };
