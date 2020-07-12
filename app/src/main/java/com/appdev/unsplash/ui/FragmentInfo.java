package com.appdev.unsplash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appdev.unsplash.R;

public class FragmentInfo extends Fragment {

    Button btnMap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        btnMap = v.findViewById(R.id.btn_map);

        btnMap.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), MapView.class));
        });


        return v;
    }


}
