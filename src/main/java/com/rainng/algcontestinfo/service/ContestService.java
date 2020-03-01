package com.rainng.algcontestinfo.service;

import com.rainng.algcontestinfo.manager.ContestManager;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.OldContestEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContestService {
    private final ContestManager manager;

    public ContestService(ContestManager manager) {
        this.manager = manager;
    }

    public List<ContestEntity> getContests() {
        return manager.getContests();
    }

    public List<ContestEntity> getAcmContests() {
        return manager.getAcmContests();
    }

    public List<ContestEntity> getOiContests() {
        return manager.getOiContests();
    }

    public List<OldContestEntity> getOldContests() {
        return convertToOldContest(getContests());
    }

    public List<OldContestEntity> getOldAcmContests() {
        return convertToOldContest(getAcmContests());
    }

    public List<OldContestEntity> getOldOiContests() {
        return convertToOldContest(getOiContests());
    }

    private List<OldContestEntity> convertToOldContest(List<ContestEntity> list) {
        List<OldContestEntity> contestList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            OldContestEntity contest = OldContestEntity.from(list.get(i));
            contest.setId(i + 1);
            contestList.add(contest);
        }

        return contestList;
    }
}
