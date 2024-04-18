export interface IAdvertising {
    advertisingId:number;
    advertiserId:number;
    sizeType:string;
    sizeValue:number;
    sizeFee: number;
    allPagesFee: number;
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