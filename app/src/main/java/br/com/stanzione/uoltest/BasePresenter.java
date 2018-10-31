package br.com.stanzione.uoltest;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void dispose();
}
