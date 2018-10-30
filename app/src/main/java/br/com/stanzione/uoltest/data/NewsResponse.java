package br.com.stanzione.uoltest.data;

import java.util.List;

public class NewsResponse {

    private List<News> newsList;

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
