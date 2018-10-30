package br.com.stanzione.uoltest.noticias;

import java.util.List;
import java.util.Observable;

import br.com.stanzione.uoltest.BasePresenter;
import br.com.stanzione.uoltest.BaseView;
import br.com.stanzione.uoltest.data.News;

public interface NoticiasFragmentContract {

    interface View extends BaseView {
        void showNews(List<News> newsList);
    }

    interface Presenter extends BasePresenter<View> {
        void getNews();
    }

    interface Model {
        Observable fetchNews();
    }

}
