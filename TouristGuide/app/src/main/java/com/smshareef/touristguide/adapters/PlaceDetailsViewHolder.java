package com.smshareef.touristguide.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.Place;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FilenameUtils;

/**
 * Created by smsha on 17-05-2017.
 *
 * ViewHolder for recycler view to display places
 *
 * @author smshareef
 */

public class PlaceDetailsViewHolder extends RecyclerView.ViewHolder{

    private ImageView placeImage;
    private TextView placeName;
    private Context context;

    public PlaceDetailsViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.placeImage = (ImageView) itemView.findViewById(R.id.imageView);
        this.placeName = (TextView) itemView.findViewById(R.id.textView);
    }

    void bindData(Place place) {
        Resources resources = context.getResources();
        int targetWidth = resources.getDimensionPixelSize(R.dimen.column_width);
        int targetHeight = resources.getDimensionPixelSize(R.dimen.column_width);

        String placeNameStr = place.getPlaceName();
        placeName.setText(FilenameUtils.removeExtension(placeNameStr));

        Picasso.with(context)
                .load(place.getPlaceImage())
                .centerCrop()
                .resize(targetWidth, targetHeight)
                .placeholder(R.mipmap.ic_launcher_round)// placeholder before loading images
                .error(R.mipmap.ic_launcher_round) // Image to be displayed in case of error
                .into(placeImage);


    }

}
