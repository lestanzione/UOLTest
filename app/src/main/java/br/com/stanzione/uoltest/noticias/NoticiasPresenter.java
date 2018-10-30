package br.com.stanzione.uoltest.noticias;

public class NoticiasPresenter implements NoticiasFragmentContract.Presenter {

    private NoticiasFragmentContract.View view;
    private NoticiasFragmentContract.Model model;

    public NoticiasPresenter(NoticiasFragmentContract.Model model){
        this.model = model;
    }

    @Override
    public void getNews() {

    }

    @Override
    public void attachView(NoticiasFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void dispose() {

    }
}
