package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class ClickNeighbourEvent {


    public Neighbour neighbour;


    public ClickNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}


