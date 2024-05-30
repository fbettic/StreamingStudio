export interface IAssociationResponse {
  platformId: number;
  transactionId: number;
  subscriberId: number;
  state: string;
  uuid: string;
  authUrl: string;
  associationType: string;
  requestAt: string;
  closedAt: string;
}
