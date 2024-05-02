package org.toudonou;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class Crawler extends Thread {
    private final int id;
    private final int timeout = 1000;

    private static boolean running = true;
    private static final List<String> urls_to_crawl = new ArrayList<>();
    private static final Set<String> result = new HashSet<String>();
    private static final Set<String> invalid_urls = new HashSet<String>();
    private static int MAX_PAGES;

    public Crawler(int id) {
        this.id = id;
    }

    public static void init(String url, int max_pages) {
        MAX_PAGES = max_pages;
        invalid_urls.addAll(getInvalidUrls("src/main/java/org/toudonou/robot.txt"));
        synchronized (urls_to_crawl) {
            urls_to_crawl.add(url);
        }
    }

    @Override
    public void run() {
        while (running) {
            String url = null;
            synchronized (urls_to_crawl) {
                if (!urls_to_crawl.isEmpty()) {
                    url = urls_to_crawl.getFirst();
                    urls_to_crawl.removeFirst();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (url != null && urlValid(url) && !result.contains(url)) {
                PageResult pageResult = crawl(url);
                synchronized (result) {
                    result.add(url);
                    for (String link : pageResult.getLinks()) {
                        if (!result.contains(link)) {
                            urls_to_crawl.add(link);
                        }
                    }

                    if (result.size() >= MAX_PAGES) {
                        stopCrawler();
                    }
                }
                //System.out.printf("Crawler %d crawled %s\n", id, url);
            }
        }
    }

    private PageResult crawl(String url) {
        Set<String> links = new HashSet<String>();
        String content = "";
        try {
            Document document = Jsoup.connect(url).timeout(timeout).get();
            content = document.text();
            Elements linksOnPage = document.select("a[href]");
            for (Element page : linksOnPage) {
                links.add(page.attr("abs:href"));
            }
        } catch (IOException e) {
            System.err.println("For '" + url + "': " + e.getMessage());
        }

        return new PageResult(url, content, links);
    }

    public void stopCrawler() {
        running = false;
    }

    private boolean urlValid(String url) {
        if (invalid_urls.contains(url)) {
            System.out.println("Invalid URL (Robot): " + url);
            return false;
        }
        return url.startsWith("https://en.wikipedia.org/");
    }

    private static Set<String> getInvalidUrls(String robot_file_path) {

        Set<String> urls = new HashSet<String>();

        try (Stream<String> lines = Files.lines(Paths.get(robot_file_path))) {
            urls = lines.collect(HashSet::new, HashSet::add, HashSet::addAll);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public static Set<String> getResult() {
        return result;
    }

    public static List<String> getUrlsToCrawl() {
        return urls_to_crawl;
    }
}
