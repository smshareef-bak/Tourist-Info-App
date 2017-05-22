package com.smshareef.touristguide.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.FamousPlace;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FilenameUtils;

/**
 * Created by smsha on 22-05-2017.
 */

public class FamousPlaceViewHolder extends RecyclerView.ViewHolder {

    private ImageView famousPlaceImage;
    private TextView famousPlaceName;
    private TextView famousPlaceDescription;
    private Context context;

    public FamousPlaceViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.famousPlaceImage = (ImageView) itemView.findViewById(R.id.famousPlacesIv);
        this.famousPlaceName = (TextView) itemView.findViewById(R.id.famousPlacesNameTv);
        this.famousPlaceDescription = (TextView) itemView.findViewById(R.id.famousPlacesDescTv);
    }

    void bindData(FamousPlace famousPlace) {

        Resources resources = context.getResources();

        String mFamousPlacesName = famousPlace.getFamousPlaceName();
        String mFamousPlacesDescription = famousPlace.getFamousPlaceDescription();

        famousPlaceName.setText(FilenameUtils.removeExtension(mFamousPlacesName));
        famousPlaceDescription.setText(mFamousPlacesDescription);
        Picasso.with(context)
                .load(famousPlace.getFamousPlaceImage())
                .placeholder(R.mipmap.ic_launcher_round)// placeholder before loading images
                .error(R.mipmap.ic_launcher_round) // Image to be displayed in case of error
                .into(famousPlaceImage);
    }
}
