package ar.edu.ubp.rest.anunciantesrest.beans;

import java.sql.Date;

public class AdvertisingBean {
    private int advertisingId;
    private int bannerId;
    private int serviceId;
    private int priority;
    private Date fromDate;
    private Date toDate;

    public int getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(int advertisingId) {
        this.advertisingId = advertisingId;
    }

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
