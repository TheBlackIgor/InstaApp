package com.example.instaapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.R;
import com.example.instaapp.models.PostType;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.PickedPhoto;
import com.example.instaapp.models.Photo;
import com.example.instaapp.views.MainActivity;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private List<Photo> photosList;
    private Context context;
    public HomePageAdapter(List<Photo> list, Context context) {
        this.photosList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public HomePageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_page_post, parent, false);
        return new HomePageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageAdapter.ViewHolder holder, int position) {
        Photo photo = photosList.get(position);
        Log.d("INFO", photo.toString());
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
            if(photo.getUrl().contains(".mp4")){
                PickedPhoto.setFiletype(PostType.VIDEO);
            }else{
                PickedPhoto.setFiletype(PostType.PHOTO);
            }

            PickedPhoto.setPostURL(IpConfig.getIp() + "/api/photos/"+photo.getId());
            PickedPhoto.setDescription(photo.getLastChange());
            PickedPhoto.setUsername(photo.getAlbum());
            PickedPhoto.setTags(photo.getTags());
            PickedPhoto.setDescription(photo.getDescription());
            PickedPhoto.setLocalization(photo.getLocalization());
            PickedPhoto.photo = photo;
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
            img = itemView.findViewById(R.id.photo);
            itemView.findViewById(R.id.homePagePost).setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 875));
            ;
        }
    }
}
