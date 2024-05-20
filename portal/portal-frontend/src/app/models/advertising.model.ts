export interface IAdvertising {
    advertisingId:number;
    advertiserId:number;
    sizeId: number;
    sizeType:string;
    sizeValue:number;
    sizeFee: number;
    width:string;
    height:string;
    allPagesFeeId: number;
    allPagesFee: number;
    priorityId: number;
    priorityType: string;
    priorityValue: number;
    priorityFee: number;
    redirectUrl: string;
    imageUrl: string;
    bannerText: string;
    referenceId: number;
    fromDate: Date;
    toDate: Date;
    targets?: number[]
}

