import dayjs from 'dayjs/esm';
import { IParkingAll } from 'app/entities/parking-all/parking-all.model';
import { IDeliveryRequestPlace } from 'app/entities/delivery-request-place/delivery-request-place.model';
import { IServices } from 'app/entities/services/services.model';
import { ICheckIn } from 'app/entities/check-in/check-in.model';
import { RQStatus } from 'app/entities/enumerations/rq-status.model';

export interface IServiceRequest {
  id: number;
  requestDate?: dayjs.Dayjs | null;
  requestThruDate?: dayjs.Dayjs | null;
  statusRequest?: RQStatus | null;
  rejecttReason?: string | null;
  requestDescription?: string | null;
  objectNumber?: string | null;
  guest?: boolean | null;
  quantity?: number | null;
  totalPrice?: number | null;
  parkingAll?: Pick<IParkingAll, 'id'> | null;
  deliveryRequestPlace?: Pick<IDeliveryRequestPlace, 'id'> | null;
  services?: Pick<IServices, 'id'> | null;
  checkIn?: Pick<ICheckIn, 'id'> | null;
}

export type NewServiceRequest = Omit<IServiceRequest, 'id'> & { id: null };
