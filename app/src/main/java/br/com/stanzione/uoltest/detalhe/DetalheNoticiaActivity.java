package br.com.stanzione.uoltest.detalhe;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import javax.inject.Inject;

import br.com.stanzione.uoltest.App;
import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.data.News;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheNoticiaActivity extends AppCompatActivity implements DetalheNoticiaContract.View {

    @Inject
    DetalheNoticiaContract.Presenter presenter;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.newsWebView)
    WebView newsWebView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String id;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_noticia);

        id = getIntent().getStringExtra("selectedNews");

        setupUi();
        setupInjector();
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.getNewsById(id);

    }

    private void setupUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupInjector(){
        ((App) (getApplicationContext()))
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    private void loadUrl(){

        newsWebView.getSettings().setJavaScriptEnabled(true);
        newsWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                setProgressBarVisible(false);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                setProgressBarVisible(false);
                showNetworkError();
                super.onReceivedError(view, request, error);
            }
        });
        newsWebView.loadUrl(news.getWebviewUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.actionShare:
                shareLink();
                return true;
            default:
                return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe_noticia, menu);
        return true;
    }

    private void shareLink() {
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle(R.string.share_url_title)
                .setText(news.getShareUrl())
                .startChooser();
    }

    @Override
    public void setNews(News news) {
        this.news = news;
        loadUrl();
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
        Snackbar.make(coordinatorLayout, getResources().getString(R.string.message_general_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(coordinatorLayout , getResources().getString(R.string.message_network_error), Snackbar.LENGTH_LONG).show();
    }
}
