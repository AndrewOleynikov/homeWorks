package com.company;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PetersonLock extends ALock {
    private final boolean[] ready;
    private volatile long victim;

    PetersonLock() {
        victim = -1;
        ready = new boolean[2];
    }

    @Override
    public void lock() {
        long id = Thread.currentThread().getId() % 2;
        long patentialId = 1 - id;
        ready[(int) id] = true;
        victim = id;
        while (ready[(int) patentialId] && victim == id) {};
    }


    @Override
    public void unlock() {
        long id = Thread.currentThread().getId() % 2;
        ready[(int) id] = false;
    }
}
