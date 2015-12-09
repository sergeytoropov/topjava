package ru.javawebinar.topjava.model;

public class Sequence {
    private int sq;

    public Sequence() {
        this.sq = 0;
    }

    public int value() {
       return sq;
    }

    public int nextValue() {
        return ++sq;
    }
}
