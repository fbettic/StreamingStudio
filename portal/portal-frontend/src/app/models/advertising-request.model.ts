export interface IAdvertisingRequest {
    advertiserId: number;
    sizeId: number;
    allPagesFeeId: number;
    priorityId: number;
    redirectUrl?: string;
    imageUrl?: string;
    bannerText?: string;
    bannerId?: string;
    fromDate: Date;
    toDate: Date;
}

sizeId,allPagesFeeId,priorityId