package br.com.stanzione.uoltest.noticias;

import java.io.IOException;

import br.com.stanzione.uoltest.data.NewsResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NoticiasPresenter implements NoticiasFragmentContract.Presenter {

    private NoticiasFragmentContract.View view;
    private NoticiasFragmentContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public NoticiasPresenter(NoticiasFragmentContract.Model model){
        this.model = model;
    }

    @Override
    public void getNews() {
        view.setProgressBarVisible(true);

        compositeDisposable.add(
                model.fetchNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onNewsReceived,
                                this::onNewsError
                        )
        );

    }

    private void onNewsReceived(NewsResponse newsResponse) {
        view.setProgressBarVisible(false);
        view.showNews(newsResponse.getNewsList());
    }

    private void onNewsError(Throwable throwable) {
        System.out.println("Inside onNewsError");
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
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
