package br.com.stanzione.uoltest.noticias;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.stanzione.uoltest.R;
import br.com.stanzione.uoltest.data.News;

public class NoticiasFragment extends Fragment implements NoticiasFragmentContract.View{


    public NoticiasFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_noticias, container, false);
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
