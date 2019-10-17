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
public class NowCoderCrawler extends BaseCrawler {
    private static final String ACM_URL = "https://ac.nowcoder.com/acm/contest/vip-index?rankTypeFilter=0";
    private static final String OI_URL = "https://ac.nowcoder.com/acm/contest/vip-index?rankTypeFilter=2";

    @Override
    public List<ContestEntity> crawl() {
        List<ContestEntity> contestList = crawl(ACM_URL);

        List<ContestEntity> oiList = crawl(OI_URL);
        for (ContestEntity contest : oiList) {
            contest.setOiContest(true);
        }
        contestList.addAll(oiList);

        return contestList;
    }

    private List<ContestEntity> crawl(String url) {
        List<ContestEntity> contestList = new ArrayList<>();

        Document doc = Jsoup.parse(get(url));
        Elements contests = doc.select("div.platform-item-cont");
        for (Element contest : contests) {
            contestList.add(parseContest(contest));
        }

        return contestList;
    }

    private ContestEntity parseContest(Element contest) {
        String name = contest.selectFirst("a").text();
        String link = "https://ac.nowcoder.com" + contest.selectFirst("a").attr("href");

        String timeRange = contest.selectFirst("li.match-time-icon").text().replace('（', '(');
        Date startTime = parseDate(subMid(timeRange, "比赛时间：", " 至 "));
        Date endTime = parseDate(subMid(timeRange, " 至 ", " ("));

        boolean register = contest.selectFirst("span.match-signup") != null;

        String status = ContestStatus.PUBLIC;
        if (register) {
            status = ContestStatus.REGISTER;
        }

        return new ContestEntity("NowCoder", name, startTime, endTime, status, link);
    }

    private Date parseDate(String time) {
        return parseDate(time, "yyyy-MM-dd HH:mm", "Asia/Shanghai");
    }
}
