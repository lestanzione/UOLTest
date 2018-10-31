package br.com.stanzione.uoltest;

import android.app.Application;

import br.com.stanzione.uoltest.di.ApplicationComponent;
import br.com.stanzione.uoltest.di.DaggerApplicationComponent;
import br.com.stanzione.uoltest.di.NetworkModule;
import br.com.stanzione.uoltest.noticias.NoticiasModule;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .noticiasModule(new NoticiasModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
