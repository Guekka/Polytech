package com.example.ds2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PodiumPokemonFragment extends Fragment {
    Podium podium = new Podium();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_podium_pokemon, container, false);
        // retrive image R.id.best1

        ImageView best1 = (ImageView) view.findViewById(R.id.best1);
        String best1Url = podium.getBest().get(0).getPictureURL();

        Picasso.get().load(best1Url).into(best1);

        ImageView best2 = (ImageView) view.findViewById(R.id.best2);
        String best2Url = podium.getBest().get(1).getPictureURL();

        Picasso.get().load(best2Url).into(best2);

        ImageView best3 = (ImageView) view.findViewById(R.id.best3);
        String best3Url = podium.getBest().get(2).getPictureURL();

        Picasso.get().load(best3Url).into(best3);

        return view;
    }
}