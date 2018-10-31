package br.com.stanzione.uoltest;

import android.app.Application;

import br.com.stanzione.uoltest.detalhe.DetalheNoticiaModule;
import br.com.stanzione.uoltest.di.AndroidModule;
import br.com.stanzione.uoltest.di.ApplicationComponent;
import br.com.stanzione.uoltest.di.DaggerApplicationComponent;
import br.com.stanzione.uoltest.di.NetworkModule;
import br.com.stanzione.uoltest.noticias.NoticiasModule;
import io.realm.Realm;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .androidModule(new AndroidModule())
                .noticiasModule(new NoticiasModule())
                .detalheNoticiaModule(new DetalheNoticiaModule())
                .build();

        Realm.init(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
