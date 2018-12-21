package com.example.ilham.smartmuseum.Utility;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ilham.smartmuseum.ItemDetail;
import com.example.ilham.smartmuseum.MainActivity;
import com.example.ilham.smartmuseum.Model.MuseumItem;
import com.example.ilham.smartmuseum.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v7.widget.RecyclerView.*;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.MuseumViewAdapter> {

    private Context mContext;
    private List<MuseumItem> data;

    public MuseumAdapter(Context mContext) {
        this.data = null;
        this.mContext = mContext;
    }

    public void setData(List<MuseumItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MuseumViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        MuseumViewAdapter mAdapter = new MuseumViewAdapter(view);

        return mAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumViewAdapter holder, final int position) {
        holder.tTitle.setText(data.get(position).getName());
        holder.tLocation.setText(data.get(position).getMuesumName());
        Glide.with(mContext).load(data.get(position).getImg()).apply(new RequestOptions().override(50,50))
                .into(holder.cImage);

        holder.itemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,ItemDetail.class);
                intent.putExtra(MainActivity.ITEM_NAME,data.get(position).getName());
                intent.putExtra(MainActivity.ITEM_DESC,data.get(position).getDescription());
                intent.putExtra(MainActivity.ITEM_IMG,data.get(position).getImg());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MuseumViewAdapter extends ViewHolder {
        CircleImageView cImage;
        TextView tTitle;
        TextView tLocation;
        RelativeLayout itemLayout;
        public MuseumViewAdapter(@NonNull View itemView) {
            super(itemView);
            cImage = itemView.findViewById(R.id.item_image);
            tTitle = itemView.findViewById(R.id.tv_title);
            itemLayout = itemView.findViewById(R.id.item_layout);
            tLocation = itemView.findViewById(R.id.tV_location);
        }
    }
}
