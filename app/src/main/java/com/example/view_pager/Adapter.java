package com.example.view_pager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fragment.LoveFragment;
import com.example.society_try.R;

import java.util.List;


public class Adapter extends PagerAdapter {

    private List<Model> item;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Model> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_test, container, false);

        ImageView imageView;
        TextView textView;

        imageView = view.findViewById(R.id.gambar_viewpager);
        textView = view.findViewById(R.id.isi);
        imageView.setImageResource(item.get(position).getImage());
        textView.setText(item.get(position).getDescription());

        if (position == item.size() - 2){
            LoveFragment.viewPager.post(runnable);
        }

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public static Runnable slide = new Runnable() {
        @Override
        public void run() {
            LoveFragment.viewPager.setCurrentItem(LoveFragment.viewPager.getCurrentItem() + 1);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            item.addAll(item);
            notifyDataSetChanged();
        }
    };
}
