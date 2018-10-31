package br.com.stanzione.uoltest.noticias;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import br.com.stanzione.uoltest.App;
import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.data.News;
import br.com.stanzione.uoltest.detalhe.DetalheNoticiaActivity;
import br.com.stanzione.uoltest.noticias.adapter.NewsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticiasFragment extends Fragment implements NoticiasFragmentContract.View, NewsAdapter.OnNewsSelectedListener {

    @Inject
    NoticiasFragmentContract.Presenter presenter;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.newsRecyclerView)
    RecyclerView newsRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private NewsAdapter adapter;

    private Boolean isStarted = false;
    private Boolean isVisible = false;


    public NoticiasFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);
        setupUi(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        setupInjector(context);
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible) {
            presenter.getNews(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            presenter.getNews(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    private void setupInjector(Context context){
        ((App) (context.getApplicationContext()))
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    private void setupUi(View view){
        ButterKnife.bind(this, view);

        adapter = new NewsAdapter(getContext(), this);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecyclerView.setAdapter(adapter);
        newsRecyclerView.addItemDecoration(new DividerItemDecoration(newsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(
                () -> presenter.getNews(true)
        );
    }

    @Override
    public void showNews(List<News> newsList) {
        adapter.setItems(newsList);
    }

    @Override
    public void setSwipeRefreshVisible(boolean visible) {
        swipeRefreshLayout.setRefreshing(visible);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
            if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showGeneralError() {
        Snackbar.make(newsRecyclerView, getResources().getString(R.string.message_general_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(newsRecyclerView , getResources().getString(R.string.message_network_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onNewsSelected(News news) {
        Intent intent = new Intent(getContext(), DetalheNoticiaActivity.class);
        intent.putExtra("selectedNews", news.getId());
        startActivity(intent);
    }
}
