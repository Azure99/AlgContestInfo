package com.rainng.algcontestinfo.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.ContestStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JiSuanKeCrawler extends BaseCrawler {
    private static final String URL = "https://www.jisuanke.com/api/contests?page=1";

    @Override
    public List<ContestEntity> crawl() {
        List<JsonNode> contestsJsonNodes = null;
        try {
            JsonNode response = new ObjectMapper().readTree(get(URL));
            contestsJsonNodes = response.findValues("contests");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON");
        }

        List<ContestEntity> contestList = new ArrayList<>();
        for (JsonNode contestsJsonNode : contestsJsonNodes) {
            for (JsonNode contestNode : contestsJsonNode) {
                contestList.add(parseContest(contestNode));
            }
        }

        return contestList;
    }

    private ContestEntity parseContest(JsonNode contestNode) {
        String name = contestNode.get("title").asText();
        Date startTime = parseDate(contestNode.get("startTime").asText(), "yyyy-MM-dd HH:mm:ss");
        Integer duration = contestNode.get("duration").asInt();
        Date endTime = Date.from(startTime.toInstant().plusSeconds(duration * 60));
        String status = ContestStatus.REGISTER;
        boolean oiContest = contestNode.get("rule").asText().equals("IOI");
        String link = "https://www.jisuanke.com/contest/" + contestNode.get("contestId").asText();
        return new ContestEntity("JiSuanKe", name, startTime, endTime, status, oiContest, link);
    }
}
