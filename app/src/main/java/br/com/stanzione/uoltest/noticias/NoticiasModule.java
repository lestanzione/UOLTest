package br.com.stanzione.uoltest.noticias;

import javax.inject.Singleton;

import br.com.stanzione.uoltest.api.UolApi;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class NoticiasModule {

    @Provides
    @Singleton
    NoticiasFragmentContract.Presenter providesPresenter(NoticiasFragmentContract.Model model){
        NoticiasPresenter presenter = new NoticiasPresenter(model);
        return presenter;
    }

    @Provides
    @Singleton
    NoticiasFragmentContract.Model providesModel(UolApi uolApi, Realm realm){
        NoticiasModel model = new NoticiasModel(uolApi, realm);
        return model;
    }

}
