package br.com.stanzione.uoltest.di;

import javax.inject.Singleton;

import br.com.stanzione.uoltest.detalhe.DetalheNoticiaActivity;
import br.com.stanzione.uoltest.detalhe.DetalheNoticiaModule;
import br.com.stanzione.uoltest.noticias.NoticiasFragment;
import br.com.stanzione.uoltest.noticias.NoticiasModule;
import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                AndroidModule.class,
                NoticiasModule.class,
                DetalheNoticiaModule.class
        }
)
public interface ApplicationComponent {
    void inject(NoticiasFragment fragment);
    void inject(DetalheNoticiaActivity activity);
}
