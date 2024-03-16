import { Role } from "../enums/role.enum";

export interface IAdministrator{
    userId:number;
    firstname:string;
    lastname:string;
    email:string;
    password?:string;
    role:Role;
}