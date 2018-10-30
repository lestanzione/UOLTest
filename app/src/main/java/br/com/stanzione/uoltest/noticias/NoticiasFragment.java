package br.com.stanzione.uoltest.noticias;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.data.News;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticiasFragment extends Fragment implements NoticiasFragmentContract.View{

    @BindView(R.id.newsRecyclerView)
    RecyclerView newsRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    public NoticiasFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);
        return view;
    }

    private void setupUi(View view){
        ButterKnife.bind(this, view);
    }

    @Override
    public void showNews(List<News> newsList) {

    }

    @Override
    public void setProgressBarVisible(boolean visible) {

    }

    @Override
    public void showGeneralError() {

    }

    @Override
    public void showNetworkError() {

    }

}
