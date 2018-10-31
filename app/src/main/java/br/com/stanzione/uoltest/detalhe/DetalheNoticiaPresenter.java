package br.com.stanzione.uoltest.detalhe;

import java.io.IOException;

import br.com.stanzione.uoltest.data.News;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetalheNoticiaPresenter implements DetalheNoticiaContract.Presenter {

    private DetalheNoticiaContract.View view;
    private DetalheNoticiaContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetalheNoticiaPresenter(DetalheNoticiaContract.Model model){
        this.model = model;
    }

    @Override
    public void getNewsById(String id) {

        view.setProgressBarVisible(true);

        compositeDisposable.add(
                model.fetchNewsById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onNewsReceived,
                                this::onNewsError
                        )
        );
    }

    @Override
    public void attachView(DetalheNoticiaContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    private void onNewsReceived(News news) {
        view.setNews(news);
    }

    private void onNewsError(Throwable throwable) {
        view.setProgressBarVisible(false);
        if(throwable instanceof IOException){
            view.showNetworkError();
        }
        else{
            view.showGeneralError();
        }
    }
}
