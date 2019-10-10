package com.rainng.algcontestinfo.crawler;

import com.rainng.algcontestinfo.models.ContestEntity;
import com.rainng.algcontestinfo.models.ContestStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CodeForcesCrawler extends BaseCrawler {
    private static final String URL = "http://codeforces.com/contests";

    @Override
    public List<ContestEntity> crawl() {
        List<ContestEntity> contestList = new ArrayList<>();

        Document doc = Jsoup.parse(get(URL));
        Elements contests = doc.selectFirst("div.datatable").select("tr[data-contestid]");
        for (Element contest : contests) {
            contestList.add(parseContest(contest));
        }

        return contestList;
    }

    private ContestEntity parseContest(Element contest) {
        Elements cols = contest.select("td");

        String id = contest.attr("data-contestid");
        String link = "https://codeforces.com/contest/" + id;
        String name = cols.get(0).text().replace(" Enter »", "");

        String timeStr = cols.get(2).text();
        String lengthStr = cols.get(3).text().trim();
        Date startTime = parseDate(timeStr, "MMM/dd/yyyy HH:mm", "Europe/Moscow");
        Date endTime = convertEndTime(startTime, lengthStr);

        boolean register = cols.get(5).text().contains("Register");

        String status = ContestStatus.PUBLIC;
        if (register) {
            status = ContestStatus.REGISTER;
        }

        return new ContestEntity("CodeForces", name, startTime, endTime, status, link);
    }

    private Date convertEndTime(Date startTime, String lengthString) {
        String pattern = lengthString.length() <= 5 ? "HH:mm" : "dd:HH:mm";
        Date length = parseDate(lengthString, pattern);

        return new Date(startTime.getTime() + length.getTime());
    }
}
