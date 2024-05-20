export interface IAdvertisingRequest {
  advertiserId: number;
  sizeId: number;
  allPagesFeeId: number;
  priorityId: number;
  redirectUrl?: string;
  imageUrl?: string;
  bannerText?: string;
  referenceId?: string;
  fromDate: Date;
  toDate: Date;
  targets?: number[];
}
