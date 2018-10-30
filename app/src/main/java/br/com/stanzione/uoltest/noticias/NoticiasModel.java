package br.com.stanzione.uoltest.noticias;


import br.com.stanzione.uoltest.api.UolApi;
import br.com.stanzione.uoltest.data.NewsResponse;
import io.reactivex.Observable;

public class NoticiasModel implements NoticiasFragmentContract.Model {

    private UolApi uolApi;

    public NoticiasModel(UolApi uolApi){
        this.uolApi = uolApi;
    }

    @Override
    public Observable<NewsResponse> fetchNews() {
        return uolApi.getNewsFeed();
    }
}
