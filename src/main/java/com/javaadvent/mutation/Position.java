package com.javaadvent.mutation;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Position {

    private final static int[] ri = { -1, -1, -1,  0, 0,  1, 1, 1 };
    private final static int[] rj = { -1,  0,  1, -1, 1, -1, 0, 1 };

    public final int i;
    public final int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (i != position.i) return false;
        if (j != position.j) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }

    public List<Position> neighbourPositions() {
        return IntStream.range(0, ri.length)
                .mapToObj(rd -> new Position(i + ri[rd], j + rj[rd]))
                .collect(toList());
    }

}
