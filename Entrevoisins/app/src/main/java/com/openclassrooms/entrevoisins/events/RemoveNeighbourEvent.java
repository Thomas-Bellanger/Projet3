package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class RemoveNeighbourEvent {

        /**
         * Neighbour to delete
         */
        public Neighbour neighbour;

        /**
         * Constructor.
         * @param neighbour
         */
        public RemoveNeighbourEvent(Neighbour neighbour) {
            this.neighbour = neighbour;
        }


}
