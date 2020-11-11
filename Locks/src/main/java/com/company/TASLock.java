package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TASLock extends ALock {
    AtomicBoolean locked = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (locked.getAndSet(true)) {};
    }


    @Override
    public void unlock() {
        locked.set(false);
    }

}



