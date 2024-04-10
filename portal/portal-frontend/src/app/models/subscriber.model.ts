import { Role } from "../enums/role.enum";

export interface ISubscriber {
  userId?: number;
  firstname: string;
  lastname: string;
  email: string;
  password?: string;
  phone: string;
  birth: Date;
}
