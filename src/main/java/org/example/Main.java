package org.example;

import org.example.service.AirportSearcher;
import org.example.utils.ArgumentParser;

public class Main {
    public static void main(String[] args) {
        int row = ArgumentParser.parseArgument(args);

        AirportSearcher airportSearcher = new AirportSearcher(row);
        airportSearcher.start();
    }
}
