package br.com.stanzione.uoltest.api;


import br.com.stanzione.uoltest.data.NewsResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UolApi {

    @GET("list/news")
    public Observable<NewsResponse> getNewsFeed();

}
