package com.rainng.algcontestinfo.service;

import com.rainng.algcontestinfo.manager.ContestManager;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.OldContestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContestService {
    @Autowired
    private ContestManager manager;

    public List<ContestEntity> getContests() {
        return manager.getContests();
    }

    public List<ContestEntity> getAcmContests() {
        return manager.getAcmContests();
    }

    public List<ContestEntity> getOiContests() {
        return manager.getOiContests();
    }

    public List<OldContestEntity> getContestsOld() {
        return convertToOldContest(getContests());
    }

    public List<OldContestEntity> getAcmContestsOld() {
        return convertToOldContest(getAcmContests());
    }

    public List<OldContestEntity> getOiContestsOld() {
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
