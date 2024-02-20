package ar.edu.ubp.soap.beans;

public class WeeklyAdvertisingReportBean {
    private int reportId;
    private int advertisingId;
    private int clicks;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(int advertisingId) {
        this.advertisingId = advertisingId;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
