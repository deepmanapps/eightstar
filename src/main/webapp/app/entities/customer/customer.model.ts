export interface ICustomer {
  id: number;
  customerId?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  streetAdress?: string | null;
  line1?: string | null;
  line2?: string | null;
  city?: string | null;
  state?: string | null;
  zipCode?: number | null;
}

export type NewCustomer = Omit<ICustomer, 'id'> & { id: null };
