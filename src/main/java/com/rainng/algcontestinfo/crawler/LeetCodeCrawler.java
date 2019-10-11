package com.rainng.algcontestinfo.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.ContestStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class LeetCodeCrawler extends BaseCrawler {
    private static final String URL = "https://leetcode.com/graphql";
    private static final String REQUEST_BODY = "{\n" +
            "\t\"operationName\": null,\n" +
            "\t\"variables\": {},\n" +
            "\t\"query\": \"{\\n  brightTitle\\n  allContests {\\n    containsPremium\\n    title\\n    cardImg\\n    titleSlug\\n    description\\n    startTime\\n    duration\\n    originStartTime\\n    isVirtual\\n    company {\\n      watermark\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\"\n" +
            "}";

    private final Map<String, String> headers = new HashMap<>();

    public LeetCodeCrawler() {
        headers.put("Content-Type", "application/json");
    }

    @Override
    public List<ContestEntity> crawl() {
        List<ContestEntity> contestList = new ArrayList<>();

        JsonNode contests = getContestsJson(post(URL, REQUEST_BODY, headers));
        Date now = new Date();
        for (JsonNode contest : contests) {
            ContestEntity contestEntity = parseContest(contest);
            // 竞赛个数太多, 提前过滤
            if (contestEntity.getEndTime().after(now)) {
                contestList.add(parseContest(contest));
            }
        }

        return contestList;
    }

    private JsonNode getContestsJson(String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(data).get("data").get("allContests");
        } catch (IOException ex) {
            ex.printStackTrace();
            return JsonNodeFactory.instance.arrayNode();
        }
    }

    private ContestEntity parseContest(JsonNode contest) {
        String nameSulg = contest.get("titleSlug").asText();
        String name = contest.get("title").asText();

        long duration = contest.get("duration").asLong() * 1000;
        Date startTime = new Date(contest.get("startTime").asLong() * 1000);
        Date endTime = new Date(startTime.getTime() + duration);

        String status = ContestStatus.REGISTER;
        String link = "https://leetcode.com/contest/" + nameSulg;

        return new ContestEntity("LeetCode", name, startTime, endTime, status, false, link);
    }
}
