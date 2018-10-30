package br.com.stanzione.uoltest.di;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import br.com.stanzione.uoltest.api.UolApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    public String providesBaseUrl() {
        return "http://app.servicos.uol.com.br/c/api/v1/";
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat("yyyyMMddHHmmss")
                                        .create()
                        ))
                .build();
    }

    @Provides
    @Singleton
    public UolApi providesApi(Retrofit retrofit) {
        return retrofit.create(UolApi.class);
    }

}
