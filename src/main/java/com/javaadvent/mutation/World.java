package com.javaadvent.mutation;

import java.util.*;
import java.util.stream.Collectors;

public class World {

    private Set<Position> alive = new HashSet<>();

    public void makeAlive(List<Position> positions) {
        positions.forEach(position -> alive.add(position));
    }

    public long countNeighbours(Position position) {
        return position.neighbourPositions()
                .stream()
                .filter(neighbour -> alive.contains(neighbour))
                .count();
    }

    public boolean isAlive(Collection<Position> position) {
        return position.stream().allMatch(p -> alive.contains(p));
    }

    public boolean isAliveNextGeneration(Position position) {
        long count = countNeighbours(position);
        return (count == 3 || (isAlive(Collections.singleton(position)) && count == 2));
    }

    public void moveToNextGeneration() {
        Set<Position> nextAlive = new HashSet<>();

        for (Position position : alive) {
            if (isAliveNextGeneration(position)) {
                nextAlive.add(position);
            }
            nextAlive.addAll(position.neighbourPositions()
                    .stream()
                    .filter(p -> isAliveNextGeneration(p))
                    .collect(Collectors.toList()));

        }
        alive = nextAlive;
    }

}
