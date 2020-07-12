package com.appdev.unsplash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.appdev.unsplash.adapter.PagesAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView nav_view;
    private RecyclerView rv_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_view = findViewById(R.id.nav_view);
        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rv_pages = findViewById(R.id.rv_pages);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        PagesAdapter listAdapter = new PagesAdapter(this);
        rv_pages.setLayoutManager(mLayoutManager);
        rv_pages.setAdapter(listAdapter);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            switch (item.getItemId()){
                case R.id.navigation_image:{
                    rv_pages.scrollToPosition(0);
                }break;

                case R.id.navigation_chosen:{
                    rv_pages.scrollToPosition(1);
                }break;

                case R.id.navigation_info:{
                    rv_pages.scrollToPosition(2);
                }break;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}
