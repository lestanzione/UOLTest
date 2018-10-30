package br.com.stanzione.uoltest.api;

import java.util.Observable;

import retrofit2.http.GET;

public interface UolApi {

    @GET("list/news")
    public Observable getNewsFeed();

}
