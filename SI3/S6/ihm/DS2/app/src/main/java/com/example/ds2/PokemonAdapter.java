package com.example.ds2;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private final List<Pokemon> mPokemons;

    public PokemonAdapter(List<Pokemon> pokemons) {
        mPokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_pokemon, parent, false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = mPokemons.get(position);
        holder.mNameTextView.setText(pokemon.getName());
        Picasso.get().load(pokemon.getPictureURL()).into(holder.mPictureImageView);

        holder.mNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.INFO, "PokemonAdapter", "Pokemon " + pokemon.getName() + " clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPokemons.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public ImageView mPictureImageView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.pokemon_name);
            mPictureImageView = itemView.findViewById(R.id.pokemon_image);
        }

    }
}