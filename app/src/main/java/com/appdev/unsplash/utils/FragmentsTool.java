package com.appdev.unsplash.utils;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.appdev.unsplash.ui.DialogFullImage;

public class FragmentsTool {

    public static void showFragment(Context context,int containerId, Fragment fragment, String fragmentTag){
        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showDialog(Context context,String imageUrl){
        Bundle args = new Bundle();
        args.putString("IMAGE_URL", imageUrl);
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        DialogFullImage dialog = new DialogFullImage();
        dialog.setArguments(args);
        dialog.show(fragmentManager, "IMAGE_DIALOG");
    }

}
