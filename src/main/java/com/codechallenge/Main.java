package com.codechallenge;

class CodeChallenge {
    public static void main(String[] args) {
        // select a map from collection
        MapHandler mapHandler = new MapHandler(MapsCollection.validMaps.get(3));
        PathFollower pathFollower = new PathFollower(mapHandler);
        PathResult result = pathFollower.followPath();

        System.out.println("Letters: " + result.collectedLetters());
        System.out.println("Path as characters: " + result.path());
    }
}