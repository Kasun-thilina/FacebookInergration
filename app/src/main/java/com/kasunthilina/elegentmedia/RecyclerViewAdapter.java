package com.kasunthilina.elegentmedia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private Context mContext;
    private List<Data> mData;
    RequestOptions options;
    private static final String TAG = "RecyclerViewAdapter";
    public RecyclerViewAdapter(Context mContext,List<Data> mData){
        this.mContext=mContext;
        this.mData=mData;

        options = new RequestOptions().centerCrop().timeout(7500);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(mContext);
        view=inflater.inflate(R.layout.row_item,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvTitle.setText(mData.get(i).getTitle());
        myViewHolder.tvDescription.setText(mData.get(i).getDescription());
        myViewHolder.tvAddress.setText(mData.get(i).getAddress());
        myViewHolder.tvPostCode.setText(mData.get(i).getPostcode());
        myViewHolder.tvPhoneNo.setText(mData.get(i).getPhoneNumber());
        //Setting up image using glide library
        Glide.with(mContext).load(mData.get(i).getImage()).placeholder(R.drawable.giphy).apply(options).into(myViewHolder.imgThumbnail);

        myViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(i));
                Intent intent = new Intent(mContext, MapsActivity.class);
                intent.putExtra("longitude", mData.get(i).getLongitude());
                intent.putExtra("latitude", mData.get(i).getLatitude());
                intent.putExtra("address", mData.get(i).getAddress());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle,tvDescription,tvAddress,tvPostCode,tvPhoneNo;
        ImageView imgThumbnail;
        LinearLayout parentLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvHeading);
            tvDescription=itemView.findViewById(R.id.tvDescription);
            tvAddress=itemView.findViewById(R.id.tvAdress);
            tvPostCode=itemView.findViewById(R.id.tvPostCode);
            tvPhoneNo=itemView.findViewById(R.id.tvPhoneNumber);
            imgThumbnail=itemView.findViewById(R.id.imgThumbnail);
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }
    }
}
