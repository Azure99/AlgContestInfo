package com.rainng.algcontestinfo.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.ContestStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LuoGuCrawler extends BaseCrawler {
    private static final String URL = "https://www.luogu.org/contest/list?page=1&_contentOnly=1";

    @Override
    public List<ContestEntity> crawl() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode data = objectMapper.readTree(get(URL)).get("currentData").get("contests").get("result");

            return parseContests(data);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<ContestEntity> parseContests(JsonNode data) {
        List<ContestEntity> contestList = new ArrayList<>();
        for (JsonNode item : data) {
            contestList.add(parseContest(item));
        }

        return contestList;
    }

    private ContestEntity parseContest(JsonNode item) {
        String name = item.get("name").asText();
        Date startTime = new Date(item.get("startTime").asLong() * 1000);
        Date endTime = new Date(item.get("endTime").asLong() * 1000);
        String status = ContestStatus.REGISTER;
        boolean oiContest = item.get("ruleType").asInt() != 2;
        String link = "https://www.luogu.org/contest/" + item.get("id").asText();

        return new ContestEntity("LuoGu", name, startTime, endTime, status, oiContest, link);
    }
}
