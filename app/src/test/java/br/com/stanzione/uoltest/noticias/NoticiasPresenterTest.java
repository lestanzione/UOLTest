package br.com.stanzione.uoltest.noticias;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.stanzione.uoltest.data.NewsResponse;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NoticiasPresenterTest {

    @Mock
    private NoticiasFragmentContract.View mockView;
    @Mock
    private NoticiasFragmentContract.Model mockModel;

    private NoticiasPresenter presenter;
    private NewsResponse newsResponse;

    @BeforeClass
    public static void setupRxSchedulers() {

        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

    }

    @AfterClass
    public static void tearDownRxSchedulers(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        presenter = new NoticiasPresenter(mockModel);
        presenter.attachView(mockView);

        createDefaultNewsResponse();
    }

    private void createDefaultNewsResponse() {
        newsResponse = new NewsResponse();
        newsResponse.setNewsList(new ArrayList<>());
    }

    @Test
    public void withNetworkShouldShowNewsFeed(){

        when(mockModel.fetchNews()).thenReturn(Observable.just(newsResponse));

        presenter.getNews();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).showNews(newsResponse.getNewsList());
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockModel, times(1)).fetchNews();

    }

    @Test
    public void withNoNetworkShouldShowNetworkError(){

        when(mockModel.fetchNews()).thenReturn(Observable.error(new IOException()));

        presenter.getNews();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).showNews(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, times(1)).showNetworkError();
        verify(mockModel, times(1)).fetchNews();

    }

    @Test
    public void withGeneralErrorShouldShowGeneralError(){

        when(mockModel.fetchNews()).thenReturn(Observable.error(new Throwable()));

        presenter.getNews();

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).showNews(any(List.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockModel, times(1)).fetchNews();

    }

}