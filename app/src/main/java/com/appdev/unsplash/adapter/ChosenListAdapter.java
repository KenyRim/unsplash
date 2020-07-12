package com.appdev.unsplash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appdev.unsplash.R;
import com.appdev.unsplash.db.ModelMain;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ChosenListAdapter extends RecyclerView.Adapter<ChosenListAdapter.MyViewHolder> {
    private Context context;
    private static RecyclerViewClickListener itemListener;
    private List<ModelMain> list;



    public interface RecyclerViewClickListener
    {
        void recyclerViewListClicked(int inListPosition,long id, int isChosen);
        void recyclerViewImageClicked(int inListPosition,long id, int isChosen);
    }



    public ChosenListAdapter(Context context,List<ModelMain> list,RecyclerViewClickListener itemListener) {
        this.context = context;
        this.list = list;
        ChosenListAdapter.itemListener = itemListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ModelMain wallpaper = list.get(position);
        String imageUrl = "";
        imageUrl = wallpaper.getSmallImageUrl();
        holder.tv_pup_date.setText(wallpaper.getPupDate());

        if (wallpaper.getIsChosen() == 1){
            holder.btn_chosen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ischosen));
        }else{
            holder.btn_chosen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unchosen));
        }

        Glide.with(context).load(imageUrl)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView thumbnail;
        private ImageButton btn_chosen;
        private TextView tv_pup_date;

        private MyViewHolder(View itemView) {
            super(itemView);

            thumbnail =  itemView.findViewById(R.id.image_thumbnail);
            thumbnail.setOnClickListener(this);
            btn_chosen =  itemView.findViewById(R.id.btn_chosen);
            btn_chosen.setOnClickListener(this);
            tv_pup_date = itemView.findViewById(R.id.tv_pup_date);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_chosen:{
                    itemListener.recyclerViewListClicked(
                            getAdapterPosition(),
                            list.get(getAdapterPosition()).getId(),
                            list.get(getAdapterPosition()).getIsChosen());
                }break;
                case R.id.image_thumbnail:{
                    itemListener.recyclerViewImageClicked(
                            getAdapterPosition(),
                            list.get(getAdapterPosition()).getId(),
                            list.get(getAdapterPosition()).getIsChosen());

                }break;
            }
        }
    }


    public void addImages(List<ModelMain> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void onUpdate(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clearList(){
        list.clear();
    }

    public List<ModelMain> getItemList(){
        return list;
    }

}