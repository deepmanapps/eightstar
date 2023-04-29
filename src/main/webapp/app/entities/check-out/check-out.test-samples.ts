import dayjs from 'dayjs/esm';

import { ICheckOut, NewCheckOut } from './check-out.model';

export const sampleWithRequiredData: ICheckOut = {
  id: 83212,
};

export const sampleWithPartialData: ICheckOut = {
  id: 39703,
  collectedAmount: 62685,
};

export const sampleWithFullData: ICheckOut = {
  id: 22235,
  roomClearance: '../fake-data/blob/hipster.txt',
  customerReview: '../fake-data/blob/hipster.txt',
  miniBarClearance: '../fake-data/blob/hipster.txt',
  lateCheckOut: dayjs('2023-04-28T14:55'),
  isLate: true,
  isCollectedDepositAmount: true,
  collectedAmount: 43902,
};

export const sampleWithNewData: NewCheckOut = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
