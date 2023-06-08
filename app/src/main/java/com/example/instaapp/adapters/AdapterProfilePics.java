package com.example.instaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.R;
import com.example.instaapp.models.Photo;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.PickedPhoto;
import com.example.instaapp.views.MainActivity;

import java.util.List;

public class AdapterProfilePics extends RecyclerView.Adapter<AdapterProfilePics.ViewHolder> {
    private List<Photo> photosList;
    private Context context;
    public AdapterProfilePics(List<Photo> list, Context context ) {
        this.photosList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterProfilePics.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_photo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProfilePics.ViewHolder holder, int position) {
        Photo photo = photosList.get(position);
        if(photo.getExtension().equals("mp4"))
            Glide.with(holder.img.getContext())
                    .load(IpConfig.getIp() + "/api/photos/"+photo.getId())
                    .skipMemoryCache(true)
                    .into(holder.img);
        else
            Glide.with(holder.img.getContext())
                    .load(IpConfig.getIp() + "/api/photos/"+photo.getId())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.img);


        holder.img.setOnClickListener(v->{
            PickedPhoto.setPostURL(IpConfig.getIp() + "/api/photos/"+photo.getId());
            PickedPhoto.setDescription(photo.getLastChange());
            PickedPhoto.setUsername(photo.getAlbum());
            PickedPhoto.setTags(photo.getTags());
            PickedPhoto.setLocalization(photo.getLocalization());
            ((MainActivity)context).showPost();
        });

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);

            itemView.findViewById(R.id.cardView).setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));;

            img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        }
    }
}
