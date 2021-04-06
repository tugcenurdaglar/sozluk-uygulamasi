package com.tugcenurdaglar.sozlukuygulamasi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient { //bağlantıyı sağlayan yapı

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){

        if (retrofit == null){ //retrofit nesnesi boşsa

            retrofit = new Retrofit.Builder().
                    baseUrl(baseUrl).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
            //gson formatında oluştu
            //gson, parse yapmadan bize hazır olarak verilen yapı

        }
        return retrofit;

    }


}
