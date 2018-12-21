package com.example.ilham.smartmuseum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private RequestCreator builder;
    private ImageView ivFragment;
    private int img;

    public AboutFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_about,container,false);
        ivFragment = v.findViewById(R.id.iv_about);
        builder.into(ivFragment);
        return v;

    }
    public void changeCurrentItem(int i){
        switch (i){
            case 0:
                builder= Picasso.with(getActivity()).load(R.drawable.antis);
                break;
            case 1:
                builder= Picasso.with(getActivity()).load(R.drawable.ilham);
                break;
            case 2:
                builder= Picasso.with(getActivity()).load(R.drawable.raihan);
                break;
        }
    }

}
