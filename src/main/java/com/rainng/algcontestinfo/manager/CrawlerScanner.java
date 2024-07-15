package com.rainng.algcontestinfo.manager;

import com.rainng.algcontestinfo.crawler.BaseCrawler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CrawlerScanner implements ApplicationContextAware {
    private ApplicationContext context;

    private BaseCrawler[] crawlers;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @PostConstruct
    public void scan() {
        Map<String, BaseCrawler> crawlerMap = context.getBeansOfType(BaseCrawler.class);
        BaseCrawler[] crawlers = new BaseCrawler[crawlerMap.size()];
        crawlerMap.values().toArray(crawlers);

        this.crawlers = crawlers;
    }

    public BaseCrawler[] getCrawlers() {
        return crawlers;
    }
}
