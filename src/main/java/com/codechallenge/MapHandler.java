package com.codechallenge;

public class MapHandler {
    protected char[][] map;

    public MapHandler(char[][] map) {
        this.map = map;
    }

    public char get(int x, int y) {
        return map[x][y];
    }

    // checks if the position is within the bounds of the map
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length;
    }

    // checks if the position is within the bounds of the map and not empty
    public boolean isInBoundsAndValid(int x, int y) {
        return isInBounds(x, y) && map[x][y] != ' ';
    }

    // find the starting position in the map
    public int[] findStart(char startChar) {
        int[] startPosition = null;
        int count = 0;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == startChar) {
                    startPosition = new int[]{row, col};
                    count++;
                }
            }
        }

        // throw error if multiple or no starting position found
        if (count == 0) {
            return null;
        } else if (count > 1) {
            throw new IllegalArgumentException("Multiple start characters found");
        }

        return startPosition;
    }
}
