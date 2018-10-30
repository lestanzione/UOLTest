package br.com.stanzione.uoltest.noticias;

import java.util.Observable;

import br.com.stanzione.uoltest.api.UolApi;

public class NoticiasModel implements NoticiasFragmentContract.Model {

    private UolApi uolApi;

    public NoticiasModel(UolApi uolApi){
        this.uolApi = uolApi;
    }

    @Override
    public Observable fetchNews() {
        return uolApi.getNewsFeed();
    }
}
