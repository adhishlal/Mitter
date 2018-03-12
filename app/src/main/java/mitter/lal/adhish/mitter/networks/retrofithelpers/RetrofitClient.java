package mitter.lal.adhish.mitter.networks.retrofithelpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import mitter.lal.adhish.mitter.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adhish on 11/03/18.
 */

public class RetrofitClient {

    public static final String BASE_URL = BuildConfig.API_BASE_URL;

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder().setLenient().create();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


}
