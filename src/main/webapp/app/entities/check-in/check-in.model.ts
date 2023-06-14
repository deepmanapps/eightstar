import dayjs from 'dayjs/esm';
import { ICheckOut } from 'app/entities/check-out/check-out.model';
import { IHotel } from 'app/entities/hotel/hotel.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { Status } from 'app/entities/enumerations/status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface ICheckIn {
  id: number;
  identityPath?: string | null;
  status?: Status | null;
  depositAmount?: number | null;
  paymentMethod?: PaymentMethod | null;
  arrivalDate?: dayjs.Dayjs | null;
  departureDate?: dayjs.Dayjs | null;
  roomType?: string | null;
  smooking?: boolean | null;
  adults?: number | null;
  children?: number | null;
  notes?: string | null;
  checkOut?: Pick<ICheckOut, 'id'> | null;
  hotel?: IHotel | null;
  customer?: ICustomer | null;
}

export type NewCheckIn = Omit<ICheckIn, 'id'> & { id: null };
