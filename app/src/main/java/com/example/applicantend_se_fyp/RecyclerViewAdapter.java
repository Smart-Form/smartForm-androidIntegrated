package com.example.applicantend_se_fyp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // Data in
    ArrayList<Service> serviceArrayList;
    Context context;
    public RecyclerViewAdapter(Context ct, ArrayList<Service> s1){
        context = ct; // Is this app?
        serviceArrayList = s1;
    }

    // viewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context); // Define LayoutInflater to parse a view
        View view = inflater.inflate(R.layout.rv_row, parent, false); // Define the view
        return new ViewHolder(view); // viewHolder 2
    }
    // ViewHolder end

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service tmp = serviceArrayList.get(position);

        holder.textview1.setText(tmp.getS_name()); // Set Title

        // posterImg
        if(!tmp.s_img.equals("none")){

            String url_path =
                    "https://firebasestorage.googleapis.com/v0/b/lwl-se-fyp-2122-grp8.appspot.com/o/posters%2F"
                            + tmp.s_img
                            + "?alt=media";

            Glide.with(this.context)
                    .load(url_path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.getImage()); // posterImg 2 // Set Image

        }
        // posterImg end
    }

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }

    // viewHolder 3
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textview1;
        ImageView imgV_poster; // posterImg 4

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textview1 = itemView.findViewById(R.id.rv_testView);
            imgV_poster = itemView.findViewById(R.id.imgView_poster); // posterImg 5

            //on Click the item itself
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(), "click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    Service tmp = serviceArrayList.get(getAdapterPosition());
                    pass_ServiceDetailActivity(tmp);
                }
            });
        }

        public ImageView getImage(){ return this.imgV_poster;} // posterImg 3

    }
    // viewHolder 3 end

    public void pass_ServiceDetailActivity(Service service) {

        // newView 4
        Intent intent = new Intent(context,ServiceDetailActivity.class);
        intent.putExtra("service_obj", service);
        Log.d("TAG", "Select service " + SelectServiceActivity.personData);
        intent.putExtra("personData", SelectServiceActivity.personData);
        context.startActivity(intent);
    }

}