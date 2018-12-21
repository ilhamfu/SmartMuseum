package com.example.ilham.smartmuseum;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ilham.smartmuseum.Interface.MuseumInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import static android.text.Html.fromHtml;

public class ItemDetail extends AppCompatActivity {
    private ImageView imgItem;
    private TextView tvDesc;
    private Toolbar tb;
    private TextView tvTitle;
    private MuseumInterface apiInterface;

    private static final String TAG = "ItemDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        //defining activity component
        imgItem =  findViewById(R.id.img_item);
        tvDesc = findViewById(R.id.tv_desc);
        tb = findViewById(R.id.toolbar_item);
        tvTitle = findViewById(R.id.tv_title_item);

        //set Retrofit client
        Log.d(TAG, "onCreate: "+getIntent().getStringExtra(MainActivity.ITEM_NAME));

        tvTitle.setText(getIntent().getStringExtra(MainActivity.ITEM_NAME));
        tvDesc.setText(fromHtml(getIntent().getStringExtra(MainActivity.ITEM_DESC)));

        Picasso.with(this).load(getIntent().getStringExtra(MainActivity.ITEM_IMG)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                imgItem.getLayoutParams().height = (int) ((double)bitmap.getHeight()/bitmap.getWidth()*width);
                imgItem.requestLayout();
                imgItem.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        //SetToolbar
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }



}
