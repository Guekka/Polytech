package com.example.ds2;

import java.util.ArrayList;
import java.util.List;

public class PokemonList {
    private static PokemonList instance = null;

    private List<Pokemon> pokemonList = new ArrayList<>();

    public static PokemonList getInstance() {
        if (instance == null) {
            instance = new PokemonList();
        }
        return instance;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }


}

