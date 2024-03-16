import { Role } from "../enums/role.enum";
import { ServiceType } from "../enums/service-type.enum";

export interface IAdvertiser {
  id: number;
  agentFirstname: string;
  agentLastname: string;
  email: string;
  password?: string;
  role?: Role;
  companyName: string;
  bussinesName: string;
  phone: string;
  apiUrl: string;
  authToken: string;
  serviceType: ServiceType;
}
