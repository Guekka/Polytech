package com.example.ds2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class Podium extends Observable {
    List<Pokemon> best ;

    public Podium() {
        update();
    }

public List<Pokemon> getBest() {
        return best;
    }

    public void update() {
        List<Pokemon> pokemonList = PokemonList.getInstance().getPokemonList();

        best = pokemonList.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());

        setChanged();
        notifyObservers();
    }

}
