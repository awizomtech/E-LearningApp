package com.awizomtech.elearning.Activity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
class AppConfigurations {

   public static final String BASE_URL = "http://edu4sports.com/api/apps/";
   /* public static final String BASE_URL = "http://192.168.1.9:3803/api/apps/";*/
    static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(AppConfigurations.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}