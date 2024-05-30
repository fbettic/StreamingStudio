export interface ISession {
  sessionId: number;
  usedAt: Date;
  subscriberId: number;
  platformId: number;
  transactionId: number;
  filmId: number;
  filmCode: string;
  sessionUrl: string;
  createdAt: Date;
}
