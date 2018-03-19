package com.facefive.meetbook;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class my_dialog extends DialogFragment{

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        return inflater.inflate(R.layout.fragment_my_dialog,null);
    }

}
