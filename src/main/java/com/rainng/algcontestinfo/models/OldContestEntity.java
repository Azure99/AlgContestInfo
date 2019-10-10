package com.rainng.algcontestinfo.models;

import lombok.Data;

import java.util.Date;

@Data
public class OldContestEntity {
    private Integer id;
    private String oj;
    private String name;
    private Date startTime;
    private Integer week;
    private String access;
    private String link;

    public OldContestEntity() {

    }

    public static OldContestEntity from(ContestEntity contest) {
        OldContestEntity oldContest = new OldContestEntity();
        oldContest.oj = contest.getOj();
        oldContest.name = contest.getName();
        oldContest.startTime = contest.getStartTime();
        oldContest.week = contest.getStartTime().getDay();
        oldContest.access = contest.getStatus();
        oldContest.link = contest.getLink();

        return oldContest;
    }
}
