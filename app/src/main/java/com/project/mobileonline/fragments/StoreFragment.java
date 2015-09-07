package com.project.mobileonline.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.project.mobileonline.R;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by Nguyen Dinh Duc on 8/29/2015.
 */
public class StoreFragment extends Fragment {
    String[] slideImages = {
            "http://www.usell.com/blog/wp-content/uploads/2014/02/smartphones.jpg",
            "http://www.towerleasing.co.uk/files/7213/6731/6530/smart_phones_new.jpg",
            "http://cdn.toptenreviews.com/rev/site/cms/category_headers/268-h_main-w.png"
    };
    ViewPager slide;
    CirclePageIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getWidgetControl();
    }

    private void initView() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
        ImageLoader.getInstance().init(config);
        slide = (ViewPager) getActivity().findViewById(R.id.view_pager);
        SlideAdapter adapter = new SlideAdapter();
        slide.setAdapter(adapter);
        indicator = (CirclePageIndicator) getActivity().findViewById(R.id.indicator);
        indicator.setViewPager(slide);
    }

    private void getWidgetControl() {

    }

    private class SlideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return slideImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView slideImage = new ImageView(getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            slideImage.setScaleType(ImageView.ScaleType.FIT_XY);
            slideImage.setLayoutParams(layoutParams);
            ImageLoader imageLoader = ImageLoader.getInstance();

            imageLoader.displayImage(slideImages[position], slideImage);
//            slideImage.setImageResource(R.drawable.header_view_bg);
            container.addView(slideImage);
            return slideImage;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}
