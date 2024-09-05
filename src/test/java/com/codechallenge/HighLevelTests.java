package com.codechallenge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HighLevelTests {

    @Test
    void testValidMaps() {
        MapHandler mapHandler = new MapHandler(null);
        PathFollower pathFollower = new PathFollower(mapHandler);
        // Expected results for collected letters and paths for each map
        String[] expectedLetters = {"ABCD", "ABCD", "ABC", "GOONIES"};
        String[] expectedPaths = {
                "@|+-+|A|+-B-C|+-+|+-D--+|x",
                "@|A+---B--+|+--C-+|-||+---D--+|x",
                "@--A||+--B|C+x",
                "@-G-O-+|+-+|O||+-O-N-+|I|+-+|+-I-+|ES|x"
        };

        for (int i = 0; i < MapsCollection.validMaps.size(); i++) {
            mapHandler.map = MapsCollection.validMaps.get(i);
            PathResult result = pathFollower.followPath();

            // Assert the expected collected letters and paths for the current map
            assertEquals(expectedLetters[i], result.collectedLetters());
            assertEquals(expectedPaths[i], result.path());
        }
    }

    @Test
    void testInvalidMaps() {

        MapHandler mapHandler = new MapHandler(MapsCollection.invalidMaps.getFirst());
        PathFollower pathFollower = new PathFollower(mapHandler);

        Exception exception = assertThrows(IllegalArgumentException.class, pathFollower::followPath);
        assertTrue(exception.getMessage().contains("Start character not found in the map"));

        mapHandler = new MapHandler(MapsCollection.invalidMaps.get(1));
        pathFollower = new PathFollower(mapHandler);

        exception = assertThrows(IllegalArgumentException.class, pathFollower::followPath);
        assertTrue(exception.getMessage().contains("Multiple start characters found"));

        mapHandler = new MapHandler(MapsCollection.invalidMaps.get(2));
        pathFollower = new PathFollower(mapHandler);

        exception = assertThrows(ArrayIndexOutOfBoundsException.class, pathFollower::followPath);
        assertTrue(exception.getMessage().contains("Next position is out of bounds or invalid"));

        mapHandler = new MapHandler(MapsCollection.invalidMaps.get(3));
        pathFollower = new PathFollower(mapHandler);

        exception = assertThrows(IllegalArgumentException.class, pathFollower::followPath);
        assertTrue(exception.getMessage().contains("Multiple or no directions"));

        mapHandler = new MapHandler(MapsCollection.invalidMaps.get(4));
        pathFollower = new PathFollower(mapHandler);

        exception = assertThrows(IllegalArgumentException.class, pathFollower::followPath);
        assertTrue(exception.getMessage().contains("Multiple or no starting directions"));
    }
}