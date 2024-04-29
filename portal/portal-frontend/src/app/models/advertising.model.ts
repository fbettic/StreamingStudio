export interface IAdvertising {
    advertisingId:number;
    advertiserId:number;
    sizeId: number;
    sizeType:string;
    sizeValue:number;
    sizeFee: number;
    allPagesFeeId: number;
    allPagesFee: number;
    priorityId: number;
    priorityType: string;
    priorityValue: number;
    priorityFee: number;
    redirectUrl: string;
    imageUrl: string;
    bannerText: string;
    bannerId: number;
    fromDate: Date;
    toDate: Date;
}

