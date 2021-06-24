package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class ClickEvent {

    public Neighbour neighbour;


    public ClickEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}

