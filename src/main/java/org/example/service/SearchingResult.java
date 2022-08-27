package org.example.service;

public class SearchingResult <T> {
    private final T result;
    private final long time;

    public SearchingResult(T result, long time) {
        this.result = result;
        this.time = time;
    }

    public T getResult() {
        return result;
    }

    public long getTime() {
        return time;
    }
}
