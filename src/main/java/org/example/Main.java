package org.example;

public class Main {
    public static void main(String[] args) {
        int row = ArgumentParser.parseArgument(args);

        AirportSearcher airportSearcher = new AirportSearcher(row);
        airportSearcher.start();
    }
}