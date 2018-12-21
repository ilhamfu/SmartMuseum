package com.example.ilham.smartmuseum.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ilham.smartmuseum.MainActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
        public static String BASE_URL;
        private static Retrofit retrofit = null;
        public static Retrofit getClient(Context c) {
            if (retrofit==null) {
                SharedPreferences pref = c.getSharedPreferences(MainActivity.APP_PREFERENCE,Context.MODE_PRIVATE);
                BASE_URL = "http://192.168.43.11:5000";

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
}
