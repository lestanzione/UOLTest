package br.com.stanzione.uoltest.noticias;

import java.util.List;

import br.com.stanzione.uoltest.BasePresenter;
import br.com.stanzione.uoltest.BaseView;
import br.com.stanzione.uoltest.data.News;
import br.com.stanzione.uoltest.data.NewsResponse;
import io.reactivex.Observable;

public interface NoticiasFragmentContract {

    interface View extends BaseView {
        void showNews(List<News> newsList);
        void showDatabaseMessage();
        void setSwipeRefreshVisible(boolean visible);
    }

    interface Presenter extends BasePresenter<View> {
        void getNews(boolean isRefresh);
    }

    interface Model {
        Observable<NewsResponse> fetchNews();
        Observable<NewsResponse> fetchDatabaseNews();
        void storeNewsList(List<News> newsList);
    }

}
