import dayjs from 'dayjs/esm';

export interface ICheckOut {
  id: number;
  roomClearance?: string | null;
  customerReview?: string | null;
  miniBarClearance?: string | null;
  lateCheckOut?: dayjs.Dayjs | null;
  isLate?: boolean | null;
  isCollectedDepositAmount?: boolean | null;
  collectedAmount?: number | null;
}

export type NewCheckOut = Omit<ICheckOut, 'id'> & { id: null };
