import { IHotel } from 'app/entities/hotel/hotel.model';
import { IServices } from 'app/entities/services/services.model';

export interface IHotelServices {
  id: number;
  active?: boolean | null;
  forGuest?: boolean | null;
  servicePrice?: number | null;
  hotel?: IHotel | null;
  services?: IServices | null;
}

export type NewHotelServices = Omit<IHotelServices, 'id'> & { id: null };
