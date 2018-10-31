package br.com.stanzione.uoltest.noticias;

import java.io.IOException;

import br.com.stanzione.uoltest.data.NewsResponse;
import br.com.stanzione.uoltest.error.NoSavedNewsError;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NoticiasPresenter implements NoticiasFragmentContract.Presenter {

    private NoticiasFragmentContract.View view;
    private NoticiasFragmentContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private boolean isFromDatabase = false;


    public NoticiasPresenter(NoticiasFragmentContract.Model model){
        this.model = model;
    }

    @Override
    public void getNews(boolean isRefresh) {

        isFromDatabase = false;

        if(!isRefresh) {
            view.setProgressBarVisible(true);
        }

        compositeDisposable.add(
                model.fetchNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(this::getFromDatabase)
                        .subscribe(
                                this::onNewsReceived,
                                this::onNewsError
                        )
        );

    }

    private Observable<NewsResponse> getFromDatabase(Throwable throwable) {
        isFromDatabase = true;
        if (throwable instanceof IOException) {
            return model.fetchDatabaseNews();
        }
        return Observable.error(throwable);
    }

    private void onNewsReceived(NewsResponse newsResponse) {
        model.storeNewsList(newsResponse.getNewsList());
        view.setSwipeRefreshVisible(false);
        view.setProgressBarVisible(false);
        view.showNews(newsResponse.getNewsList());

        if(isFromDatabase){
            view.showDatabaseMessage();
        }
    }

    private void onNewsError(Throwable throwable) {
        throwable.printStackTrace();
        view.setSwipeRefreshVisible(false);
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
            view.showNetworkError();
        }
        else if(throwable instanceof NoSavedNewsError){
            view.showNetworkError();
        }
        else{
            view.showGeneralError();
        }
    }

    @Override
    public void attachView(NoticiasFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
