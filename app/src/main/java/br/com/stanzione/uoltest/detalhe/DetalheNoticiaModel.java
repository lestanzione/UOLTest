package br.com.stanzione.uoltest.detalhe;

import br.com.stanzione.uoltest.data.News;
import io.reactivex.Observable;

public class DetalheNoticiaModel implements DetalheNoticiaContract.Model {
    @Override
    public Observable<News> fetchNewsById(String id) {
        return null;
    }
}
