package br.com.stanzione.uoltest.noticias;


import java.util.List;

import br.com.stanzione.uoltest.api.UolApi;
import br.com.stanzione.uoltest.data.News;
import br.com.stanzione.uoltest.data.NewsResponse;
import io.reactivex.Observable;
import io.realm.Realm;

public class NoticiasModel implements NoticiasFragmentContract.Model {

    private UolApi uolApi;
    private Realm realm;

    public NoticiasModel(UolApi uolApi, Realm realm){
        this.uolApi = uolApi;
        this.realm = realm;
    }

    @Override
    public Observable<NewsResponse> fetchNews() {
        return uolApi.getNewsFeed();
    }

    @Override
    public void storeNewsList(List<News> newsList) {
        realm.executeTransactionAsync(realm -> {
            realm.delete(News.class);
            realm.copyToRealm(newsList);
        });
    }
}
