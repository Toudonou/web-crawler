package org.toudonou;

import java.util.ArrayList;
import java.util.List;
public class  Scheduler {
    private final List<Crawler> crawlers = new ArrayList<>();
    private final int nbrCrawlers;

    public Scheduler(int nbrCrawlers, String url, int maxPages) {
        this.nbrCrawlers = nbrCrawlers;
        Crawler.init(url, maxPages);
        for (int i = 0; i < nbrCrawlers; i++) {
            crawlers.add(new Crawler(i + 1));
        }
    }

    public void start() {
        for (Crawler crawler : crawlers) {
            crawler.start();
        }

        for (Crawler crawler : crawlers) {
            try {
                crawler.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Scheduler finished crawling " + Crawler.getResult().size() + " pages");
        System.out.println("Remaining pages: " + Crawler.getUrlsToCrawl().size());
    }
}

//
//public class Scheduler extends Thread {
//    private Set<String> urls_to_crawl = null;
//    private Set<String> result = null;
//    private boolean running = true;
//    private List<Crawler> crawlers;
//    private int nbr_crawlers;
//
//    public Scheduler(String url, int nbr_crawlers) {
//        this.urls_to_crawl = new HashSet<String>();
//        this.urls_to_crawl.add(url);
//        this.result = new HashSet<String>();
//
//        this.nbr_crawlers = nbr_crawlers;
//        this.crawlers = new ArrayList<Crawler>();
//        for (int i = 0; i < this.nbr_crawlers; i++) {
//            this.crawlers.add(new Crawler(i + 1, url));
//        }
//        for (Crawler crawler : this.crawlers) {
//            crawler.start();
//        }
//    }
//
//    @Override
//    public void run() {
//        while (running) {
//            synchronized (this.result) {
//                if (this.result.size() >= 1000) {
//                    break;
//                }
//            }
//        }
//
//        for (Crawler crawler : this.crawlers) {
//            crawler.stopCrawler();
//        }
//        for (Crawler crawler : this.crawlers) {
//            try {
//                crawler.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        System.out.printf("Scheduler finished crawling %d pages\n", this.result.size());
//    }
//
//    public String peekUrl() {
//        if (this.urls_to_crawl.isEmpty()) {
//            return null;
//        }
//        String url = this.urls_to_crawl.iterator().next();
//        this.urls_to_crawl.remove(url);
//        return url;
//    }
//
//    public void addResult(PageResult pageResult) {
//        Set<String> links = pageResult.getLinks();
//        this.result.add(pageResult.getUrl());
//        for (String link : links) {
//            if (!this.result.contains(link) && urlValid(link)) {
//                this.urls_to_crawl.add(link);
//            }
//        }
//    }
//
//    private boolean urlValid(String url) {
//        return url.startsWith("https://en.wikipedia.org/");
//    }
//
//    public void stopScheduler() {
//        this.running = false;
//    }
//}
