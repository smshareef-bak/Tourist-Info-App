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

import static com.smshareef.touristguide.utils.AppConstants.KEY_PLACE;
import static com.smshareef.touristguide.utils.AppConstants.KEY_POSITION;

/**
 * Created by smsha on 17-05-2017.
 */

public class PlaceRecyclerAdapter extends RecyclerView.Adapter<PlaceDetailsViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Place> placeArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public PlaceRecyclerAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.placeArrayList = new ArrayList<>();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setData(ArrayList<Place> placeArrayList) {
        this.placeArrayList.addAll(placeArrayList);
        notifyDataSetChanged();
    }

    public void addData(Place place) {
        this.placeArrayList.add(place);
        notifyItemInserted(this.placeArrayList.size());
    }

    @Override
    public PlaceDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_content_main_activity, parent, false);
        return new PlaceDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlaceDetailsViewHolder holder, int position) {

        final Place place = placeArrayList.get(holder.getAdapterPosition());

        holder.bindData(place);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onRecyclerViewItemClickListener) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(KEY_POSITION, holder.getAdapterPosition());
                    bundle.putParcelable(KEY_PLACE, place);
                    onRecyclerViewItemClickListener.onItemClicked(bundle);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }
}
