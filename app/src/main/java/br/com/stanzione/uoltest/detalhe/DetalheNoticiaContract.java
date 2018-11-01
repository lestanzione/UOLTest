package br.com.stanzione.uoltest.detalhe;

import br.com.stanzione.uoltest.BasePresenter;
import br.com.stanzione.uoltest.BaseView;
import br.com.stanzione.uoltest.data.News;
import io.reactivex.Observable;

public interface DetalheNoticiaContract {

    interface View extends BaseView{
        void setNews(News news);
    }

    interface Presenter extends BasePresenter<View> {
        void getNewsById(String id);
    }

    interface Model {
        Observable<News> fetchNewsById(String id);
    }

}
