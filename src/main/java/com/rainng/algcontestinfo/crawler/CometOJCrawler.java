package com.rainng.algcontestinfo.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.ContestStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CometOJCrawler extends BaseCrawler {
    private static final String ACM_URL = "https://cometoj.com/v2/contests?offset=0&limit=8&rule_type=ACM&page=1";
    private static final String OI_URL = "https://cometoj.com/v2/contests?offset=0&limit=8&rule_type=OI&page=1";

    @Override
    public List<ContestEntity> crawl() {
        List<ContestEntity> oiList = crawl(OI_URL);
        for (ContestEntity contest : oiList) {
            contest.setOiContest(true);
        }

        List<ContestEntity> contestList = crawl(ACM_URL);
        contestList.addAll(oiList);

        return contestList;
    }

    private List<ContestEntity> crawl(String url) {
        List<ContestEntity> contestList = new ArrayList<>();

        JsonNode contests = getContestsJson(url);
        for (JsonNode contest : contests) {
            contestList.add(parseContest(contest));
        }

        return contestList;
    }

    private JsonNode getContestsJson(String url) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = (ArrayNode) mapper.readTree(get(url)).get("data").get("results");
            arrayNode.add(mapper.readTree(get(url)).get("data").get("top"));

            return arrayNode;
        } catch (IOException ex) {
            ex.printStackTrace();
            return JsonNodeFactory.instance.arrayNode();
        }
    }

    private ContestEntity parseContest(JsonNode contest) {
        int id = contest.get("id").asInt();
        String title = contest.get("title").asText();

        Date startTime = parseDate(contest.get("start_time"));
        Date endTime = parseDate(contest.get("end_time"));

        boolean pub = contest.get("contest_type").asText().equals("Public");
        boolean register = contest.get("status").asInt() == 1;

        String status = ContestStatus.PRIVATE;
        if (pub && register) {
            status = ContestStatus.REGISTER;
        } else if (pub) {
            status = ContestStatus.PUBLIC;
        }

        return new ContestEntity("CometOJ", title, startTime, endTime, status, "https://cometoj.com/contest/" + id);
    }

    private Date parseDate(JsonNode timeProp) {
        String timeStr = timeProp.asText().replace("T", " ").replace("+08:00", "");
        return parseDate(timeStr, "yyyy-MM-dd HH:mm:ss", "Asia/Shanghai");
    }
}
