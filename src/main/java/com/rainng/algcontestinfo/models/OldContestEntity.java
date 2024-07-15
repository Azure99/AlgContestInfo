package com.rainng.algcontestinfo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
