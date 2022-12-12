package com.example.bbcnewsreader;

import java.io.Serializable;

public class RssItem implements Serializable {
    public String title;
    public String link;
    public String linkId;
    public String description;
    public String pubDate;

    public RssItem(){
        this.linkId = "";
    }

    public RssItem(String title, String link, String description, String pubDate, String linkId) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.linkId = linkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
}
