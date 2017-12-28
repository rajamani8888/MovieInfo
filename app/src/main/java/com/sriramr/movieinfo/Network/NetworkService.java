package com.sriramr.movieinfo.Network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by sriramr on 02/12/17.
 */

public class NetworkService {

    public static MovieService getService(Context context){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        File cacheFile = new File(context.getCacheDir(), "okhttp-cahce");

        Cache cache = new Cache(cacheFile, 10*1000*1000 ); // 10 MB Cache

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(client);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .build();

        return retrofit.create(MovieService.class);

    }

}
