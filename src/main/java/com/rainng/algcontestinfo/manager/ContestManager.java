package com.rainng.algcontestinfo.manager;

import com.rainng.algcontestinfo.crawler.BaseCrawler;
import com.rainng.algcontestinfo.models.ContestEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EnableScheduling
@Component
public class ContestManager {
    private static final int OJ_LIMIT = 3;
    private static final int SUM_LIMIT = 20;

    private final CrawlerScanner crawlerScanner;
    private Map<String, Set<ContestEntity>> contestMap = new HashMap<>();

    public ContestManager(CrawlerScanner crawlerScanner) {
        this.crawlerScanner = crawlerScanner;
    }

    public List<ContestEntity> getContests() {
        return query(x -> true);
    }

    public List<ContestEntity> getAcmContests() {
        return query(x -> !x.isOiContest());
    }

    public List<ContestEntity> getOiContests() {
        return query(x -> x.isOiContest());
    }

    private List<ContestEntity> query(Predicate<? super ContestEntity> cond) {
        List<ContestEntity> contestList = new ArrayList<>();

        for (Set<ContestEntity> set : contestMap.values()) {
            contestList.addAll(queryFromOJ(set, cond));
        }

        return contestList.stream()
                .sorted()
                .limit(SUM_LIMIT)
                .collect(Collectors.toList());
    }

    private List<ContestEntity> queryFromOJ(Set<ContestEntity> originSet, Predicate<? super ContestEntity> cond) {
        Date now = new Date();
        return originSet.stream()
                .sorted()
                .filter(x -> x.getEndTime().after(now))
                .filter(cond)
                .limit(OJ_LIMIT)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void crawlTask() {
        Map<String, Set<ContestEntity>> newMap = new HashMap<>();

        for (BaseCrawler crawler : crawlerScanner.getCrawlers()) {
            String name = crawler.getClass().getSimpleName().replace("Crawler", "");
            Set<ContestEntity> set = new TreeSet<>(crawlOne(crawler));

            newMap.put(name, set);
        }

        contestMap = newMap;
    }

    private List<ContestEntity> crawlOne(BaseCrawler crawler) {
        try {
            return crawler.crawl();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
