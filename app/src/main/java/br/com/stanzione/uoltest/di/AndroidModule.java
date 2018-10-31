package br.com.stanzione.uoltest.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class AndroidModule {

    @Provides
    @Singleton
    public Realm providesRealm() {
        return Realm.getDefaultInstance();
    }

}
