package com.example.espresso;

/**
 * Created by s2s8tb on 2019/8/29.
 */

public class SingleTone {

    static class SingleTonHolder {
        static final SingleTone INSTANCE = new SingleTone();
    }

    private SingleTone() {
    }

    public SingleTone getInstance() {
        return SingleTonHolder.INSTANCE;
    }
}
