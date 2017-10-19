package net.ycii.entity;

import java.util.List;

public class MediaContent
{
    private List<MediaNews> news_item;

    public List<MediaNews> getNews_item()
    {
        return news_item;
    }

    public void setNews_item( List<MediaNews> news_item )
    {
        this.news_item = news_item;
    }
}
