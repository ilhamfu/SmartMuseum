package com.example.ilham.smartmuseum.Utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class RetryCallback<T> implements Callback<T> {
    private int cRetry=0;
    private int maxRetry;
    private Context c;
    protected RetryCallback(@NonNull int maxRetry, @NonNull Context c){
        this.maxRetry=maxRetry;
        this.c = c;
    }

    @Override
    public void onFailure(Call<T> call, Throwable T){
        if (cRetry<maxRetry){
            call.clone().enqueue(this);
            cRetry+=1;
        }else{
            Toast.makeText(c,"Cannot Request Data",Toast.LENGTH_SHORT).show();
        }


    }
}
