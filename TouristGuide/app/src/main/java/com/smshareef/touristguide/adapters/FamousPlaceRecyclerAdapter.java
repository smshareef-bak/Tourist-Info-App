package com.smshareef.touristguide.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.FamousPlace;
import com.smshareef.touristguide.utils.OnRecyclerViewFamousPlaceClickListener;

import java.util.ArrayList;

import static com.smshareef.touristguide.utils.AppConstants.KEY_PLACE;
import static com.smshareef.touristguide.utils.AppConstants.KEY_POSITION;

/**
 * Created by smsha on 22-05-2017.
 */

public class FamousPlaceRecyclerAdapter extends RecyclerView.Adapter<FamousPlaceViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<FamousPlace> famousPlaceArrayList;
    private OnRecyclerViewFamousPlaceClickListener onRecyclerViewItemClickListener;

    public FamousPlaceRecyclerAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.famousPlaceArrayList = new ArrayList<>();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewFamousPlaceClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setData(ArrayList<FamousPlace> famousPlacesArrayList) {
        this.famousPlaceArrayList.addAll(famousPlacesArrayList);
        notifyDataSetChanged();
    }

    public void addData(FamousPlace famousPlaces) {
        this.famousPlaceArrayList.add(famousPlaces);
        notifyItemInserted(this.famousPlaceArrayList.size());
    }

    @Override
    public FamousPlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_content_famous_places, parent, false);
        return new FamousPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FamousPlaceViewHolder holder, int position) {

        final FamousPlace famousPlaces = famousPlaceArrayList.get(holder.getAdapterPosition());

        holder.bindData(famousPlaces);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onRecyclerViewItemClickListener) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(KEY_POSITION, holder.getAdapterPosition());
                    bundle.putParcelable(KEY_PLACE, famousPlaces);
                    onRecyclerViewItemClickListener.onItemClicked(bundle, v);
                }
            }
        });

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(KEY_POSITION, holder.getAdapterPosition());
                bundle.putParcelable(KEY_PLACE, famousPlaces);
                onRecyclerViewItemClickListener.onItemClicked(bundle, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return famousPlaceArrayList.size();
    }
}
