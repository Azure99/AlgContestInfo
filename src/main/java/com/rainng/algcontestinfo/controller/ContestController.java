package com.rainng.algcontestinfo.controller;

import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.OldContestEntity;
import com.rainng.algcontestinfo.service.ContestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContestController {
    private final ContestService service;

    public ContestController(ContestService service) {
        this.service = service;
    }

    @RequestMapping({"/", "/contests"})
    public List<ContestEntity> getContests() {
        return service.getContests();
    }

    @RequestMapping({"/acm", "/contests/acm"})
    public List<ContestEntity> getAcmContests() {
        return service.getAcmContests();
    }

    @RequestMapping({"/oi", "/contests/oi"})
    public List<ContestEntity> getOiContests() {
        return service.getOiContests();
    }

    @RequestMapping({"/contests.json", "/contests/old"})
    public List<OldContestEntity> getContestsOld() {
        return service.getContestsOld();
    }

    @RequestMapping({"/acm-contests.json", "/contests/old/acm"})
    public List<OldContestEntity> getAcmContestsOld() {
        return service.getAcmContestsOld();
    }

    @RequestMapping({"/oi-contests.json", "/contests/old/oi"})
    public List<OldContestEntity> getOiContestsOld() {
        return service.getOiContestsOld();
    }
}
