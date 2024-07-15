package com.rainng.algcontestinfo.models;


import java.util.Date;

public class ContestEntity implements Comparable {
    private String oj;
    private String name;
    private Date startTime;
    private Long startTimeStamp;
    private Date endTime;
    private Long endTimeStamp;
    private String status;
    private boolean oiContest;
    private String link;

    public ContestEntity(String oj, String name, Date startTime, Date endTime, String status, String link) {
        this(oj, name, startTime, endTime, status, false, link);
    }

    public ContestEntity(String oj, String name, Date startTime, Date endTime, String status, boolean oiContest, String link) {
        this.oj = oj;
        this.name = name;
        this.startTime = startTime;
        this.startTimeStamp = startTime.getTime() / 1000;
        this.endTime = endTime;
        this.endTimeStamp = endTime.getTime() / 1000;
        this.status = status;
        this.oiContest = oiContest;
        this.link = link;
        updateStatus();
    }

    public String getOj() {
        return oj;
    }

    public void setOj(String oj) {
        this.oj = oj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(Long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOiContest() {
        return oiContest;
    }

    public void setOiContest(boolean oiContest) {
        this.oiContest = oiContest;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void updateStatus() {
        Date now = new Date();
        if (now.after(startTime) && now.before(endTime)) {
            status = ContestStatus.RUNNING;
        } else if (now.after(endTime)) {
            status = ContestStatus.ENDED;
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ContestEntity i) {

            int cmp = startTime.compareTo(i.startTime);
            if (cmp == 0) {
                cmp = oj.compareTo(i.oj);
            }
            if (cmp == 0) {
                cmp = name.compareTo(i.name);
            }

            return cmp;
        }

        return 0;
    }
}
