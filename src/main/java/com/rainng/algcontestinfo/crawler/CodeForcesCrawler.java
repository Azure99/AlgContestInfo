package com.rainng.algcontestinfo.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.ContestStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CodeForcesCrawler extends BaseCrawler {
    private static final String URL = "https://codeforces.com/api/contest.list";

    @Override
    public List<ContestEntity> crawl() {
        List<ContestEntity> contestList = new ArrayList<>();

        JsonNode contests = fetchContestsJson();
        if (contests == null || !contests.isArray()) {
            return contestList;
        }

        for (JsonNode contest : contests) {
            contestList.add(parseContest(contest));
        }

        return contestList;
    }

    private JsonNode fetchContestsJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(get(URL));
            if (!"OK".equals(root.path("status").asText())) {
                return null;
            }
            return root.path("result");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private ContestEntity parseContest(JsonNode contest) {
        String id = contest.path("id").asText();
        String link = "https://codeforces.com/contest/" + id;
        String name = contest.path("name").asText();

        Date startTime = new Date(contest.path("startTimeSeconds").asLong() * 1000);
        long durationSeconds = contest.path("durationSeconds").asLong();
        Date endTime = new Date(startTime.getTime() + durationSeconds * 1000);

        String phase = contest.path("phase").asText();
        boolean register = "BEFORE".equals(phase);

        String status = ContestStatus.PUBLIC;
        if (register) {
            status = ContestStatus.REGISTER;
        }

        return new ContestEntity("CodeForces", name, startTime, endTime, status, link);
    }
}
