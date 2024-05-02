package org.toudonou;

import java.util.HashSet;
import java.util.Set;

public class PageResult implements Comparable<PageResult> {
    private final String url;
    private final String content;
    private final Set<String> links = new HashSet<>();

    public PageResult(String url, String content, Set<String> links) {
        this.url = url;
        this.content = content;
        this.links.addAll(links);
    }

    public String getUrl() {
        return url;
    }

    public Set<String> getLinks() {
        return links;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int compareTo(PageResult o) {
        return this.url.compareTo(o.url);
    }
}
