package com.rainng.algcontestinfo.models;

import lombok.Data;

import java.util.Date;

@Data
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

    public ContestEntity() {

    }

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
        if (o instanceof ContestEntity) {
            ContestEntity i = (ContestEntity) o;

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
