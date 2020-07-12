package com.appdev.unsplash.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appdev.unsplash.R;
import com.appdev.unsplash.adapter.ImageListAdapter;
import com.appdev.unsplash.api.ApiService;
import com.appdev.unsplash.db.DbMainContent;
import com.appdev.unsplash.db.ModelMain;
import com.appdev.unsplash.model.Wallpaper;
import com.appdev.unsplash.utils.FragmentsTool;
import com.appdev.unsplash.utils.MyBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentImages extends Fragment implements ImageListAdapter.RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rv_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageListAdapter listAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    private DbMainContent db;
    private ArrayList<ModelMain> wallSFromDb = new ArrayList<>();
    private ArrayList<Wallpaper> wallsFromApi = new ArrayList<>();

    @Override
    public void recyclerViewListClicked(int position,long id,int isChosen) {


        if (isChosen == 0) {
            db.update(id, 1);
            listAdapter.onUpdate(position, 1);
            Toast.makeText(getContext(),"Added to chosen!",Toast.LENGTH_SHORT).show();
        } else {
            db.update(id, 0);
            listAdapter.onUpdate(position, 0);
            Toast.makeText(getContext(),"Removed from chosen!",Toast.LENGTH_SHORT).show();
        }

        EventBus.getDefault().post(new MyBus("FRAG_IMAGES"));

        Log.e("position "+ position,id+" - "+isChosen+" - "+db.selectAll().get(position).getIsChosen());

    }

    @Override
    public void recyclerViewImageClicked(int inListPosition,long id,int isChosen) {

        FragmentsTool.showDialog(getContext(),wallSFromDb.get(inListPosition).getLargeImageUrl());

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_images, container, false);
        context = getActivity();
        EventBus.getDefault().register(this);
        db = new DbMainContent(context);

        rv_list = v.findViewById(R.id.rv_list);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        mLayoutManager = new LinearLayoutManager(context);
        listAdapter = new ImageListAdapter(context,wallSFromDb, this);
        rv_list.setLayoutManager(mLayoutManager);
        Objects.requireNonNull(rv_list.getItemAnimator()).setChangeDuration(0);
        rv_list.setAdapter(listAdapter);

        getApiContent();
        Log.e("onCreateView","onCreateView");

        return v;
    }


    private void addData(List<Wallpaper> list){
        swipeRefreshLayout.setRefreshing(false);
        for (int i = 0; i < list.size(); i++) {

            ModelMain modelMain = new ModelMain(
                    0,
                    list.get(i).getCreatedAt(),
                    list.get(i).getUrls().getSmall(),
                    list.get(i).getUrls().getFull(),
                    0
            );

            db.insert(modelMain);

        }

        wallSFromDb.clear();
        wallSFromDb = db.selectAll();
        listAdapter.addImages(wallSFromDb);
        Log.e("onComplete","posts cnt = " + wallSFromDb.size());
        swipeRefreshLayout.setRefreshing(false);
    }



    private void getApiContent() {
        Observable<List<Wallpaper>> service1 = ApiService.Factory.createService(1);
        Observable<List<Wallpaper>> service2 = ApiService.Factory.createService(2);

        service1
                .delay(100,TimeUnit.MILLISECONDS)
                .mergeWith(service2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Wallpaper>>() {
                    @Override
                    public void onNext(@NonNull List<Wallpaper> list) {
                        wallsFromApi.addAll(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"Connection failed!",Toast.LENGTH_SHORT).show();
                        addData(wallsFromApi);
                    }

                    @Override
                    public void onComplete() {
                        addData(wallsFromApi);
                    }
                });

    }

    @Subscribe/*notify recyclerview on page of chosen*/
    public void update(MyBus event){
        if (event.FRAGMENT_TAG.equals("FRAG_CHOSEN")) {
            wallSFromDb = db.selectAll();
            listAdapter.clearList();
            listAdapter.addImages(wallSFromDb);
        }
    }

    @Override
    public void onRefresh() {
        getApiContent();
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }
}
