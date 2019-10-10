package com.rainng.algcontestinfo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
public class OldContestEntity {
    private Integer id;
    private String oj;
    private String name;

    @JsonProperty("start_time")
    private Date startTime;

    private String week;
    private String access;
    private String link;

    public OldContestEntity() {

    }

    public static OldContestEntity from(ContestEntity contest) {
        OldContestEntity oldContest = new OldContestEntity();
        oldContest.oj = contest.getOj();
        oldContest.name = contest.getName();
        oldContest.startTime = contest.getStartTime();
        oldContest.week = new SimpleDateFormat("EEEE", Locale.US).format(contest.getStartTime());
        oldContest.access = contest.getStatus();
        oldContest.link = contest.getLink();

        return oldContest;
    }
}
