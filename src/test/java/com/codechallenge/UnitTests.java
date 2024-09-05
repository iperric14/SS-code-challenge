package com.codechallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnitTests {
    private MapHandler mapHandler;
    private PathFollower pathFollower;

    @BeforeEach
    void setUp() {
        char[][] map = {
                {' ', ' ', ' ', ' ', 'x', ' ', ' '},
                {'@', '-', 'A', ' ', 'B', ' ', ' '},
                {' ', ' ', '|', ' ', '|', ' ', ' '},
                {' ', ' ', '+', '-', '+', ' ', ' '}
        };

        mapHandler = new MapHandler(map);
        pathFollower = new PathFollower(mapHandler);
    }


    @Test
    void testIsInBounds() {
        assertTrue(mapHandler.isInBounds(1, 4));
        assertTrue(mapHandler.isInBounds(2, 0));
        // out of map bounds
        assertFalse(mapHandler.isInBounds(1, 8));
    }

    @Test
    void testIsInBoundsAndValid() {
        assertTrue(mapHandler.isInBoundsAndValid(1, 4));
        // in map bounds but not valid
        assertFalse(mapHandler.isInBoundsAndValid(2, 0));
    }

    @Test
    void testFindStart() {
        assertArrayEquals(new int[]{1, 0}, mapHandler.findStart('@'));

        // test with multiple starting points
        char[][] map1 = {
                {' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {'@', '-', 'A', '-', 'B', '-', '@'},
                {' ', ' ', ' ', ' ', ' ', ' ', ' '}
        };

        MapHandler mapHandler1 = new MapHandler(map1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> mapHandler1.findStart('@'));
        assertTrue(exception.getMessage().contains("Multiple start characters found"));
    }

    @Test
    void testGetNextPosition() {
        assertArrayEquals(new int[]{1, 2}, pathFollower.getNextPosition(1, 1, 'R'));
        assertArrayEquals(new int[]{1, 4}, pathFollower.getNextPosition(2, 4, 'U'));
    }

    @Test
    void testDetermineStartingDirection() {
        assertEquals('R', pathFollower.determineStartingDirection(1, 0));
    }

    @Test
    void testDetermineDirection() {
        assertEquals('D', pathFollower.determineDirection(1, 2, 'R', true));
        assertEquals('R', pathFollower.determineDirection(3, 2, 'D', false));
        assertEquals('U', pathFollower.determineDirection(3, 4, 'R', false));
    }

    @Test
    void testCheckVerticalDirection() {
        assertEquals('D', pathFollower.checkVerticalDirection(1, 2));

        char[][] map1 = {
                {' ', ' ', 'A', '-', '+', ' ', ' '},
                {'@', ' ', '|', ' ', 'B', ' ', ' '},
                {'+', '-', '+', ' ', '|', ' ', ' '},
                {' ', ' ', '|', ' ', '|', ' ', ' '},
                {' ', ' ', '+', '-', 'x', ' ', ' '}
        };

        mapHandler = new MapHandler(map1);
        pathFollower = new PathFollower(mapHandler);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pathFollower.checkVerticalDirection(2, 2));
        assertTrue(exception.getMessage().contains("Multiple or no directions"));
    }

    @Test
    void testCheckHorizontalDirection() {
        assertEquals('R', pathFollower.checkHorizontalDirection(3, 2));

        char[][] map1 = {
                {' ', ' ', 'A', '-', '+', ' ', ' '},
                {'@', ' ', ' ', ' ', 'B', ' ', ' '},
                {'+', '-', '+', ' ', '|', ' ', ' '},
                {' ', ' ', ' ', ' ', '|', ' ', ' '},
                {' ', ' ', '+', '-', 'x', ' ', ' '}
        };

        mapHandler = new MapHandler(map1);
        pathFollower = new PathFollower(mapHandler);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pathFollower.checkHorizontalDirection(2, 3));
        assertTrue(exception.getMessage().contains("Multiple or no directions"));
    }
}