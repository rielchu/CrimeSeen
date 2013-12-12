package com.example.crime_maps;

import com.example.crime_maps.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HotFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {

      View rootView = inflater.inflate(R.layout.fragment_hot, container, false);
       
      return rootView;
  }
}