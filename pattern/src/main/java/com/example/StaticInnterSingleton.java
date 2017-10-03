package com.example;

/**
 * Created by heruijun on 2017/9/27.
 */

public class StaticInnterSingleton {

    private StaticInnterSingleton() {
    }

    public static StaticInnterSingleton getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final StaticInnterSingleton sInstance = new StaticInnterSingleton();
    }
}
