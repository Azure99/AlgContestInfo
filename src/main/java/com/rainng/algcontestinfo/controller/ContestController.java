package com.rainng.algcontestinfo.controller;

import com.rainng.algcontestinfo.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContestController {
    @Autowired
    private ContestService service;

    @RequestMapping({"/", "/contest/get"})
    public Object getContests() {
        return service.getContests();
    }

    @RequestMapping({"/acm", "/contest/getAcm"})
    public Object getAcmContests() {
        return service.getAcmContests();
    }

    @RequestMapping({"/oi", "/contest/getOi"})
    public Object getOiContests() {
        return service.getOiContests();
    }

    @RequestMapping({"/contests.json", "/contest/old/get"})
    public Object getContestsOld() {
        return service.getContestsOld();
    }

    @RequestMapping({"/acmcontests.json", "/contest/old/getAcm"})
    public Object getAcmContestsOld() {
        return service.getAcmContestsOld();
    }

    @RequestMapping({"/oicontests.json", "/contest/old/getOi"})
    public Object getOiContestsOld() {
        return service.getOiContestsOld();
    }
}
