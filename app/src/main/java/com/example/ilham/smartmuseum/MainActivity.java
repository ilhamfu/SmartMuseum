package com.example.ilham.smartmuseum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.ilham.smartmuseum.Interface.MuseumInterface;
import com.example.ilham.smartmuseum.Model.MuseumCollection;
import com.example.ilham.smartmuseum.Model.MuseumItem;
import com.example.ilham.smartmuseum.Utility.ApiClient;
import com.example.ilham.smartmuseum.Utility.MuseumAdapter;
import com.example.ilham.smartmuseum.Utility.RetryCallback;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    public static final String APP_PREFERENCE = "MUSEUM_APP";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_DESC = "ITEM_DESC";
    public static final String ITEM_IMG = "ITEM_IMG";

    private RecyclerView rvList;
    private ImageView ivMain;
    private Toolbar tb;
    private SwipeRefreshLayout srl;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private MuseumInterface apiInterface;
    private MuseumAdapter listAdapter;


    final Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Defining Component
        rvList = findViewById(R.id.rv_list);
        ivMain = findViewById(R.id.iv_main);
        srl = findViewById(R.id.swipe_refresh);

        //Seting recycle view
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new MuseumAdapter(this);

        //REST
        apiInterface = ApiClient.getClient(getApplicationContext()).create(MuseumInterface.class);

        tb = findViewById(R.id.toolbar_main);
        setSupportActionBar(tb);


        Picasso.with(this).load(R.drawable.toolbar_image).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                ivMain.getLayoutParams().height = (int) ((double)bitmap.getHeight()/bitmap.getWidth()*width);
                ivMain.requestLayout();
                ivMain.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
                fetchData();
            }
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchData();
            }
        });

    }

    private void fetchData(){
        fetchData("");
    }

    private void fetchData(String name){
        Call<MuseumCollection> getMuseumCall = apiInterface.getMuseum(name);

        getMuseumCall.enqueue(new RetryCallback<MuseumCollection>(5,getApplicationContext()) {
            @Override
            public void onResponse(Call<MuseumCollection> call, Response<MuseumCollection> response) {
                List<MuseumItem> item = response.body().getItems();
                listAdapter.setData(item);
                rvList.setAdapter(listAdapter);
                Log.d(TAG, "onResponse: OnResponse");
                if (srl.isRefreshing()){
                    srl.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MuseumCollection> call, Throwable t) {
                super.onFailure(call,t);
                if (srl.isRefreshing()){
                    srl.setRefreshing(false);
                }
            }
        });
    }

    private void fetchItem(int position){
        Call<MuseumItem> museumItemCall = apiInterface.getItem(position);
        museumItemCall.enqueue(new Callback<MuseumItem>() {
            @Override
            public void onResponse(Call<MuseumItem> call, Response<MuseumItem> response) {
                if (response.body().getId()==-1){
                    Toast.makeText(getApplicationContext(),"QR code salah",Toast.LENGTH_SHORT);
                }else {
                    Intent intent = new Intent(MainActivity.this, ItemDetail.class);
                    intent.putExtra(ITEM_NAME,response.body().getName());
                    intent.putExtra(ITEM_DESC,response.body().getDescription());
                    intent.putExtra(ITEM_IMG,response.body().getImg());
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<MuseumItem> call, Throwable t) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        tb.inflateMenu(R.menu.main_activity_menu);

        SearchView searchView= (SearchView) tb.getMenu().findItem(R.id.action_searcb).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        tb.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_about:
                startActivity(new Intent(this,About.class));
                break;
            case R.id.mn_refresh:
                fetchData();
                break;
            case R.id.mn_scan:
                new IntentIntegrator(this).initiateScan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                try {
                    int id = Integer.parseInt(result.getContents());
                    fetchItem(id);
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"QR code that you enter is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isURLValid(String url){
        return Patterns.WEB_URL.matcher("https://"+url).matches();

    }
}
