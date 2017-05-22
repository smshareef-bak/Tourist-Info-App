package com.smshareef.touristguide.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.Place;
import com.smshareef.touristguide.utils.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

import static com.smshareef.touristguide.utils.AppConstants.KEY_IMAGE;
import static com.smshareef.touristguide.utils.AppConstants.KEY_IMAGE_POS;

/**
 * Created by smsha on 22-05-2017.
 */

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryImageViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Place> galleryArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public GalleryRecyclerAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.galleryArrayList = new ArrayList<>();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setData(ArrayList<Place> galleryArrayList) {
        this.galleryArrayList.addAll(galleryArrayList);
        notifyDataSetChanged();
    }

    public void addData(Place gallery) {
        this.galleryArrayList.add(gallery);
        notifyItemInserted(this.galleryArrayList.size());
        notifyItemChanged(this.galleryArrayList.size());
    }

    @Override
    public GalleryImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_content_gallery, parent, false);
        return new GalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryImageViewHolder holder, int position) {

        final Place gallery = galleryArrayList.get(holder.getAdapterPosition());

        holder.bindData(gallery);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onRecyclerViewItemClickListener) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(KEY_IMAGE_POS, holder.getAdapterPosition());
                    bundle.putParcelable(KEY_IMAGE, gallery);
                    onRecyclerViewItemClickListener.onItemClicked(bundle);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryArrayList.size();
    }
}
