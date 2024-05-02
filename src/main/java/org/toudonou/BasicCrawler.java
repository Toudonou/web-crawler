package org.toudonou;


import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class BasicCrawler {

    private HashSet<String> links;

    public BasicCrawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(String URL) {
        //4. Check if you have already crawled the URLs
        //(we are intentionally not checking for duplicate content in this example)
        if (!links.contains(URL)) {
            try {
                //4. (i) If not add it to the index
                if (links.add(URL)) {
                    System.out.println(URL);
                }
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
            } catch (HttpStatusException e) {
                System.err.println("Http Status '" + URL + "': " + e.getMessage());
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        //1. Pick a URL from the frontier
        new BasicCrawler().getPageLinks("https://en.wikipedia.org/wiki/Main_Page");
    }

}

