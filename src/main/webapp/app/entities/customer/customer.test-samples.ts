import { ICustomer, NewCustomer } from './customer.model';

export const sampleWithRequiredData: ICustomer = {
  id: 24379,
  customerId: 32779,
};

export const sampleWithPartialData: ICustomer = {
  id: 80941,
  customerId: 75018,
  firstName: 'Kari',
  lastName: 'Lebsack',
  streetAdress: 'Global Kentucky',
  line1: 'invoice',
  line2: 'Cotton payment',
};

export const sampleWithFullData: ICustomer = {
  id: 26718,
  customerId: 30781,
  firstName: 'Odie',
  lastName: 'Ruecker',
  email: 'Magali_Herzog@yahoo.com',
  phoneNumber: 'Maine',
  streetAdress: 'Market Euro North',
  line1: 'Maine',
  line2: 'silver transmitter',
  city: 'North Ettieton',
  state: 'Games Analyst',
  zipCode: 76239,
};

export const sampleWithNewData: NewCustomer = {
  customerId: 63962,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
