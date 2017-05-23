package com.smshareef.touristguide.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.Place;
import com.squareup.picasso.Picasso;

/**
 * Created by smsha on 22-05-2017.
 *
 * ViewHolder for recycler view to display images in Gallery
 *
 * @author smshareef
 */

public class GalleryImageViewHolder extends RecyclerView.ViewHolder{

    private ImageView galleryImage;
    private Context context;

    public GalleryImageViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.galleryImage = (ImageView) itemView.findViewById(R.id.galleryImageView);
    }

    void bindData(Place gallery) {

        Picasso.with(context)
                .load(gallery.getPlaceImage())
                .placeholder(R.mipmap.ic_launcher_round)// placeholder before loading images
                .error(R.mipmap.ic_launcher_round) // Image to be displayed in case of error
                .into(galleryImage);


    }

}

