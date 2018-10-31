package br.com.stanzione.uoltest.detalhe;

import javax.inject.Singleton;

import br.com.stanzione.uoltest.api.UolApi;
import br.com.stanzione.uoltest.noticias.NoticiasFragmentContract;
import br.com.stanzione.uoltest.noticias.NoticiasModel;
import br.com.stanzione.uoltest.noticias.NoticiasPresenter;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DetalheNoticiaModule {

    @Provides
    @Singleton
    DetalheNoticiaContract.Presenter providesPresenter(DetalheNoticiaContract.Model model){
        DetalheNoticiaPresenter presenter = new DetalheNoticiaPresenter(model);
        return presenter;
    }

    @Provides
    @Singleton
    DetalheNoticiaContract.Model providesModel(Realm realm){
        DetalheNoticiaModel model = new DetalheNoticiaModel(realm);
        return model;
    }

}
