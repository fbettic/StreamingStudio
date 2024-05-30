import { IAdvertising } from "./advertising.model"

export interface IAdvertisingSlot {
    slotId: string
    sizeType: string
    advertising?: IAdvertising
}