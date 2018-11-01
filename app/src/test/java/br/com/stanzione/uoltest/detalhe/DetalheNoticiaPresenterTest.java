package br.com.stanzione.uoltest.detalhe;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import br.com.stanzione.uoltest.data.News;
import br.com.stanzione.uoltest.error.NoSavedNewsError;
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

public class DetalheNoticiaPresenterTest {

    @Mock
    private DetalheNoticiaContract.View mockView;
    @Mock
    private DetalheNoticiaContract.Model mockModel;

    private DetalheNoticiaPresenter presenter;
    private News news;
    private String defaultId = "id";

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

        presenter = new DetalheNoticiaPresenter(mockModel);
        presenter.attachView(mockView);

        createDefaultNews();
    }

    private void createDefaultNews(){
        news = new News();
    }

    @Test
    public void withExistingNewsShouldLoadNewsUrl(){

        when(mockModel.fetchNewsById(anyString())).thenReturn(Observable.just(news));

        presenter.getNewsById(defaultId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, times(1)).setNews(news);
        verify(mockView, never()).setProgressBarVisible(false);
        verify(mockView, never()).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockModel, times(1)).fetchNewsById(defaultId);

    }

    @Test
    public void withNonExistingNewsShouldShowGeneralMessage(){

        when(mockModel.fetchNewsById(anyString())).thenReturn(Observable.error(new NoSavedNewsError()));

        presenter.getNewsById(defaultId);

        verify(mockView, times(1)).setProgressBarVisible(true);
        verify(mockView, never()).setNews(any(News.class));
        verify(mockView, times(1)).setProgressBarVisible(false);
        verify(mockView, times(1)).showGeneralError();
        verify(mockView, never()).showNetworkError();
        verify(mockModel, times(1)).fetchNewsById(defaultId);

    }

}