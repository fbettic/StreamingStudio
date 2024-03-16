import { Role } from "../enums/role.enum";

export interface ISubscriber {
  userId: number;
  firstname: string;
  lastname: string;
  email: string;
  password?: string;
  role: Role;
  phone: string;
  validated: boolean;
  birth: Date;
}
