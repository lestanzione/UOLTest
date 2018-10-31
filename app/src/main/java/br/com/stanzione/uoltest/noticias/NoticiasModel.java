package br.com.stanzione.uoltest.noticias;


import java.util.List;

import br.com.stanzione.uoltest.api.UolApi;
import br.com.stanzione.uoltest.data.News;
import br.com.stanzione.uoltest.data.NewsResponse;
import br.com.stanzione.uoltest.error.NoSavedNewsError;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

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
    public Observable<NewsResponse> fetchDatabaseNews() {

        RealmResults<News> newsRealmResults = realm.where(News.class).findAll();

        if(newsRealmResults.isEmpty()){
            return Observable.error(new NoSavedNewsError());
        }

        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setNewsList(realm.copyFromRealm(newsRealmResults));
        return Observable.just(newsResponse);
    }

    @Override
    public void storeNewsList(List<News> newsList) {
        realm.executeTransactionAsync(realm -> {
            realm.delete(News.class);
            realm.copyToRealm(newsList);
        });
    }
}
