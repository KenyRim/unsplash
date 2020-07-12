package com.appdev.unsplash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.appdev.unsplash.R;
import com.appdev.unsplash.ui.FragmentChosen;
import com.appdev.unsplash.ui.FragmentImages;
import com.appdev.unsplash.ui.FragmentInfo;
import com.appdev.unsplash.utils.FragmentsTool;

public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.MyViewHolder> {
    private Context ctx;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout container;

        private MyViewHolder(View itemView) {
            super(itemView);
            container =  itemView.findViewById(R.id.container);
        }


    }

    public PagesAdapter(Context context) {
        ctx = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        final View itemView = LayoutInflater.from(ctx).inflate(R.layout.page_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        switch (position){
            case 0: {
                FragmentsTool.showFragment(ctx,holder.container.getId(),new FragmentImages(),"FRAG_IMAGES");
                break;
            }
            case 1: {
                FragmentsTool.showFragment(ctx,holder.container.getId(),new FragmentChosen(),"FRAG_CHOSEN");
                break;
            }
            case 2: {
                FragmentsTool.showFragment(ctx,holder.container.getId(),new FragmentInfo(),"FRAG_INFO");
                break;
            }

        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}