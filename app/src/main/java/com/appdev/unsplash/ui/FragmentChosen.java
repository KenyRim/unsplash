package com.appdev.unsplash.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appdev.unsplash.R;
import com.appdev.unsplash.adapter.ChosenListAdapter;
import com.appdev.unsplash.db.DbMainContent;
import com.appdev.unsplash.db.ModelMain;
import com.appdev.unsplash.utils.FragmentsTool;
import com.appdev.unsplash.utils.MyBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentChosen extends Fragment implements
        ChosenListAdapter.RecyclerViewClickListener {

    private RecyclerView rv_list;
    private ChosenListAdapter listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private DbMainContent db;
    private ArrayList<ModelMain> wallpaperList = new ArrayList<>();

    @Override
    public void recyclerViewListClicked(int inListPosition,long id,int isChosen) {

        db.update(id, 0);
        listAdapter.onUpdate(inListPosition);
        EventBus.getDefault().post(new MyBus("FRAG_CHOSEN"));
        Toast.makeText(getContext(),"Removed from chosen!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void recyclerViewImageClicked(int inListPosition,long id,int isChosen) {
        FragmentsTool.showDialog(getContext(),wallpaperList.get(inListPosition).getLargeImageUrl());

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chosen, container, false);
        context = getContext();
        EventBus.getDefault().register(this);
        db = new DbMainContent(context);

        rv_list = v.findViewById(R.id.rv_list);

        mLayoutManager = new LinearLayoutManager(context);
        listAdapter = new ChosenListAdapter(context,wallpaperList, this);
        rv_list.setLayoutManager(mLayoutManager);
        Objects.requireNonNull(rv_list.getItemAnimator()).setChangeDuration(0);
        rv_list.setAdapter(listAdapter);
        wallpaperList = db.selectAllChosen();
        listAdapter.addImages(wallpaperList);


        return v;
    }

    @Subscribe/*notify recyclerview on page of images*/
    public void update(MyBus event){
        if (event.FRAGMENT_TAG.equals("FRAG_IMAGES")) {
            wallpaperList = db.selectAllChosen();
            listAdapter.clearList();
            listAdapter.addImages(wallpaperList);
        }

    }

}