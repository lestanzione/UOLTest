package br.com.stanzione.uoltest.detalhe;

import br.com.stanzione.uoltest.data.News;
import io.reactivex.Observable;
import io.realm.Realm;

public class DetalheNoticiaModel implements DetalheNoticiaContract.Model {

    private Realm realm;

    public DetalheNoticiaModel(Realm realm){
        this.realm = realm;
    }

    @Override
    public Observable<News> fetchNewsById(String id) {
        News news = realm.where(News.class).equalTo(News.COLUMN_ID, id).findFirst();
        if(null == news){
            return Observable.error(new Throwable());
        }
        else {
            return Observable.just(news);
        }
    }
}
