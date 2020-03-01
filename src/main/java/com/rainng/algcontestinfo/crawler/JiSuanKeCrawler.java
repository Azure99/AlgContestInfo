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
public class JiSuanKeCrawler extends BaseCrawler {
    private static final String URL = "https://nanti.jisuanke.com/contest?page=1";

    @Override
    public List<ContestEntity> crawl() {
        List<ContestEntity> contestList = new ArrayList<>();

        Document doc = Jsoup.parse(get(URL));
        Elements contests = doc.selectFirst("tbody").select("tr");
        for (Element contest : contests) {
            contestList.add(parseContest(contest));
        }

        return contestList;
    }

    private ContestEntity parseContest(Element element) {
        Elements cols = element.select("td");

        String name = cols.get(1).text();
        Date startTime = parseDate(cols.get(3).text(), "yyyy-MM-dd HH:mm", "Asia/Shanghai");
        Date endTime = convertEndTime(startTime, cols.get(4).text());
        String status = ContestStatus.REGISTER;
        boolean oiContest = cols.get(0).text().contains("IOI");
        String link = "https:" + cols.get(1).selectFirst("a").attr("href");

        return new ContestEntity("JiSuanKe", name, startTime, endTime, status, oiContest, link);
    }

    private Date convertEndTime(Date startTime, String lengthString) {
        int hour = Integer.parseInt(subStart(lengthString, " 小时 "));
        int min = Integer.parseInt(subMid(lengthString, " 小时 ", " 分钟"));
        int length = hour * 60 * 60 * 1000;
        length += min * 60 * 1000;

        return new Date(startTime.getTime() + length);
    }
}
