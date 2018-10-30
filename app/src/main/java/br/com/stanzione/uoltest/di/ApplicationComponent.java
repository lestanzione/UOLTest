package br.com.stanzione.uoltest.di;

import javax.inject.Singleton;

import br.com.stanzione.uoltest.noticias.NoticiasFragment;
import br.com.stanzione.uoltest.noticias.NoticiasModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                NoticiasModule.class
        }
)
public interface ApplicationComponent {
    void inject(NoticiasFragment fragment);
}
