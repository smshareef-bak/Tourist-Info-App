package com.smshareef.touristguide.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import com.smshareef.touristguide.R;
import com.smshareef.touristguide.adapters.GalleryFullScreenPagerAdapter;
import com.smshareef.touristguide.model.Place;
import com.smshareef.touristguide.utils.ImmersiveModeFragment;
import com.smshareef.touristguide.utils.PlaceUtils;

import java.util.ArrayList;

/**
 * Created by smsha on 22-05-2017.
 */

public class GalleryFullScreenActivity extends FragmentActivity{

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    ArrayList<Place> images;
    String dir;

    public static final String FRAGTAG = "ImmersiveModeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.activity_fullscreen_gallery);

        if (getSupportFragmentManager().findFragmentByTag(FRAGTAG) == null ) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ImmersiveModeFragment fragment = new ImmersiveModeFragment();
            transaction.add(fragment, FRAGTAG);
            transaction.commit();
        }

        dir = getIntent().getStringExtra("dir");
        images = PlaceUtils.getAllImagesFromDir(dir);

        viewPager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new GalleryFullScreenPagerAdapter(GalleryFullScreenActivity.this, images);

        viewPager.setAdapter(pagerAdapter);

    }



}
