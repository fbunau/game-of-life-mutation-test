package com.javaadvent.mutation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestWorld {

    private World world;
    private WorldStateFactory worldStateFactory;

    @Before
    public void setUp() {
        world = new World();
        worldStateFactory = new WorldStateFactory();
    }

    // Taken from https://github.com/marcoemrich/game-of-life-rules/blob/master/gol_rules.pdf

    @Test
    public void givenPositionInWorld_countNeighbours() {
        world.makeAlive(Arrays.asList($(0, 1), $(1, 2), $(2, 0), $(1, 1)));
        assertEquals(3, world.countNeighbours($(1, 1)));
    }

    @Test
    public void givenUnderPopulatedPosition__CellDeadNextGeneration()
    {
        world.makeAlive(Arrays.asList($(1, 1), $(2, 2)));
        assertFalse(world.isAliveNextGeneration($(1, 1)));
    }

    @Test
    public void givenSurvivalPosition__CellAliveNextGeneration()
    {
        world.makeAlive(Arrays.asList($(1, 0), $(1, 1), $(2, 1), $(2, 2)));
        assertTrue(world.isAliveNextGeneration($(1, 1)));
    }

    @Test
    public void givenOverCrowdingPosition__CellDeadNextGeneration()
    {
        world.makeAlive(Arrays.asList($(1, 1)));
        world.makeAlive($(1, 1).neighbourPositions());
        assertFalse(world.isAliveNextGeneration($(1, 1)));
    }

    @Test
    public void givenReproductionPosition__CellAliveNextGeneration()
    {
        world.makeAlive(Arrays.asList($(1, 0), $(2, 1), $(2, 2)));
        assertTrue(world.isAliveNextGeneration($(1, 1)));
    }

    @Test
    public void givenBlockWorld__WorldRemainsStill() {
        givenWorldState__nextWorldStateIsTheSame(worldStateFactory.block());
    }

    @Test
    public void givenLoafWorld__WorldRemainsStill() {
        givenWorldState__nextWorldStateIsTheSame(worldStateFactory.loaf());
    }

    @Test
    public void givenToadOscillator__WorldOscillates() {
        givenWorldStateSequences__WorldEvolvesAsExpected(worldStateFactory.toad());
    }



//    // With this commented out we have a mutation testing fail
//    // Uncomment to have mutation test pass 100%
//
//    @Test
//    public void givenGlider_WorldEvolvesAsExpected() {
//        givenWorldStateSequences__WorldEvolvesAsExpected(worldStateFactory.glider());
//    }

    // Tests from : http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
    private class WorldStateFactory {

        private Position[][] block() {
            return new Position[][]{
                    { $(1, 1), $(1, 2), $(2, 1), $(2, 2) }
            };
        }

        private Position[][] loaf() {
            return new Position[][]{
                    { $(1, 2), $(1, 3), $(2, 1), $(2, 4), $(3, 2), $(3, 4), $(4, 3) }
            };
        }

        private Position[][] toad() {
            return new Position[][] {
                    { $(2, 2), $(2, 3), $(2, 4), $(3, 1), $(3, 2), $(3, 3) },
                    { $(1, 3), $(2, 1), $(2, 4), $(3, 1), $(3, 4), $(4, 2) },
                    { $(2, 2), $(2, 3), $(2, 4), $(3, 1), $(3, 2), $(3, 3) },
            };
        }

//        // With this commented out we have a mutation testing fail
//        // Uncomment to have mutation test pass 100%
//
//        private Position[][] glider() {
//            return new Position[][] {
//                    { $(1, 3), $(2, 4), $(3, 2), $(3, 3), $(3, 4) },
//                    { $(2, 2), $(2, 4), $(3, 4), $(4, 3) },
//                    { $(2, 4), $(3, 2), $(3, 4), $(4, 3), $(4, 4) },
//                    { $(2, 3), $(3, 4), $(3, 5), $(4, 3), $(4, 4) },
//                    { $(2, 4), $(3, 5), $(4, 3), $(4, 4), $(4, 5) }
//            };
//        }
    }

    private void givenWorldState__nextWorldStateIsTheSame(Position[][] worldState) {
        givenWorldState__nextWorldStateIsExpectedState(worldState[0], worldState[0]);
    }

    private void givenWorldStateSequences__WorldEvolvesAsExpected(Position[][] worldStates)
    {
        for (int i = 0; i < worldStates.length-1; ++i) {
            givenWorldState__nextWorldStateIsExpectedState(worldStates[i], worldStates[i+1]);
        }
    }

    private void givenWorldState__nextWorldStateIsExpectedState(Position[] worldState1,
                                                               Position[] worldState2)
    {
        world.makeAlive(Arrays.asList(worldState1));
        world.moveToNextGeneration();
        assertTrue(world.isAlive(Arrays.asList(worldState2)));
    }

    private Position $(int i, int j) {
        return new Position(i, j);
    }

}
