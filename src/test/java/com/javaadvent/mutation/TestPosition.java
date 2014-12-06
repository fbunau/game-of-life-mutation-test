package com.javaadvent.mutation;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestPosition {

    @Test
    public void givenPosition_neighbourPositionsAreComputedCorrectly()
    {
        Position pos = new Position(10, 10);

        List<Position> expectedNeighbours = Arrays.asList(
                $(9, 9), $(9, 10), $(9, 11),
                $(10, 9), $(10, 11),
                $(11, 9), $(11, 10), $(11, 11)
        );

        assertEquals(expectedNeighbours, pos.neighbourPositions());
    }

    private Position $(int i, int j) {
        return new Position(i, j);
    }
}
