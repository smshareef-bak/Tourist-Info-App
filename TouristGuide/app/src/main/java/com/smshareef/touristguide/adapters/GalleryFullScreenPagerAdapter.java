package com.smshareef.touristguide.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.model.Place;

import java.util.ArrayList;

/**
 * Created by smsha on 22-05-2017
 *
 * Adapter to show images in Pager
 *
 * @author smsha
 */

public class GalleryFullScreenPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Place> images;

    public GalleryFullScreenPagerAdapter(Context context, ArrayList<Place> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView fullScreenIv;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fullscreen_pager_item, container, false);

        fullScreenIv = (ImageView) itemView.findViewById(R.id.fullScreenIv);

        fullScreenIv.setImageURI(images.get(position).getPlaceImage());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
}
