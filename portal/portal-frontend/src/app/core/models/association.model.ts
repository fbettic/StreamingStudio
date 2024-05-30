export interface IAssociation {
  transactionId: number;
  platformId: number;
  subscriberId: number;
  userToken: string;
  entryDate: Date;
  leavingDate: Date;
}
