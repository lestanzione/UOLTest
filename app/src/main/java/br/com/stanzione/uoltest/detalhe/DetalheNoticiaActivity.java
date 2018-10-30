package br.com.stanzione.uoltest.detalhe;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import br.com.stanzione.uoltest.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalheNoticiaActivity extends AppCompatActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.newsWebView)
    WebView newsWebView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String url;
    private String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_noticia);

        url = getIntent().getStringExtra("selectedNews");
        shareUrl = getIntent().getStringExtra("selectedNewsShare");

        setupUi();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUrl();

    }

    private void setupUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notícias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadUrl(){

        progressBar.setVisibility(View.VISIBLE);

        newsWebView.getSettings().setJavaScriptEnabled(true);
        newsWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }
        });
        newsWebView.loadUrl(url);
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
                .setChooserTitle("Share URL")
                .setText(shareUrl)
                .startChooser();
    }

}
