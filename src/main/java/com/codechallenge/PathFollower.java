package com.codechallenge;

import java.util.*;

class PathFollower {
    private final MapHandler mapHandler;
    private static final char START = '@';
    private static final char END = 'x';
    private static final char TURN = '+';

    public PathFollower(MapHandler mapHandler) {
        this.mapHandler = mapHandler;
    }

    public PathResult followPath() {
        int[] startPosition = mapHandler.findStart(START); // get position of the "START" character
        if (startPosition == null) {
            throw new IllegalArgumentException("Start character not found in the map");
        }

        StringBuilder collectedLetters = new StringBuilder();
        StringBuilder path = new StringBuilder();
        Set<String> visitedPaths = new HashSet<>(); // prevent collecting letters twice

        int x = startPosition[0];
        int y = startPosition[1];
        char direction = determineStartingDirection(x, y);

        while (true) {
            if (!mapHandler.isInBounds(x, y)) {
                throw new ArrayIndexOutOfBoundsException("Next position is out of bounds");
            }

            char currentChar = mapHandler.get(x, y);
            boolean isLetter = Character.isLetter(currentChar);
            path.append(currentChar);

            // key to store in visitedPaths
            String positionKey = x + "," + y;
            if (currentChar == END) {
                break;
            } else if (isLetter && !visitedPaths.contains(positionKey)) {
                collectedLetters.append(currentChar);
                visitedPaths.add(positionKey);
            }

            // check if we need to change direction
            if (currentChar == TURN || isLetter) {
                direction = determineDirection(x, y, direction, isLetter);
            }

            // get next position based on direction
            int[] nextPosition = getNextPosition(x, y, direction);

            // update position if within bounds and not a space
            if (mapHandler.isInBoundsAndValid(nextPosition[0], nextPosition[1])) {
                x = nextPosition[0];
                y = nextPosition[1];
            } else {
                throw new ArrayIndexOutOfBoundsException("Next position is out of bounds or invalid");
            }
        }

        return new PathResult(collectedLetters.toString(), path.toString());
    }

    // determine next position based on direction
    protected int[] getNextPosition(int x, int y, char direction) {
        return switch (direction) {
            case 'R' -> new int[]{x, y + 1};
            case 'L' -> new int[]{x, y - 1};
            case 'U' -> new int[]{x - 1, y};
            case 'D' -> new int[]{x + 1, y};
            default -> throw new IllegalArgumentException("Invalid direction");
        };
    }

    // check all directions and determine the starting direction
    // throws exception if multiple or no starting directions
    protected char determineStartingDirection(int x, int y) {
        List<Character> possibleDirections = new ArrayList<>();

        addDirectionIfValid(possibleDirections, x + 1, y, 'D');
        addDirectionIfValid(possibleDirections, x - 1, y, 'U');
        addDirectionIfValid(possibleDirections, x, y + 1, 'R');
        addDirectionIfValid(possibleDirections, x, y - 1, 'L');

        if (possibleDirections.size() != 1) {
            throw new IllegalArgumentException("Multiple or no starting directions");
        }

        return possibleDirections.getFirst();
    }

    protected char determineDirection(int x, int y, char currentDirection, boolean isLetter) {
        // if current character is letter, check next position in current direction
        // if next position is not valid, change direction
        if (isLetter) {
            int[] nextPosition = getNextPosition(x, y, currentDirection);
            boolean continuePath = mapHandler.isInBoundsAndValid(nextPosition[0], nextPosition[1]);

            if (continuePath) {
                return currentDirection;
            }
        }

        return switch (currentDirection) {
            case 'R', 'L' -> checkVerticalDirection(x, y);
            case 'U', 'D' -> checkHorizontalDirection(x, y);
            default -> currentDirection;
        };
    }

    // checks vertical direction, if multiple or no directions, throw error
    protected char checkVerticalDirection(int x, int y) {
        List<Character> possibleDirections = new ArrayList<>();
        addDirectionIfValid(possibleDirections, x + 1, y, 'D');
        addDirectionIfValid(possibleDirections, x - 1, y, 'U');

        if (possibleDirections.size() != 1) {
            throw new IllegalArgumentException("Multiple or no directions");
        }

        return possibleDirections.getFirst();
    }

    // checks horizontal direction, if multiple or no directions, throw error
    protected char checkHorizontalDirection(int x, int y) {
        List<Character> possibleDirections = new ArrayList<>();
        addDirectionIfValid(possibleDirections, x, y + 1, 'R');
        addDirectionIfValid(possibleDirections, x, y - 1, 'L');

        if (possibleDirections.size() != 1) {
            throw new IllegalArgumentException("Multiple or no directions");
        }

        return possibleDirections.getFirst();
    }

    private void addDirectionIfValid(List<Character> directions, int x, int y, char direction) {
        if (mapHandler.isInBoundsAndValid(x, y)) {
            directions.add(direction);
        }
    }
}

// class to store the result of following a path
record PathResult(String collectedLetters, String path) {
}
