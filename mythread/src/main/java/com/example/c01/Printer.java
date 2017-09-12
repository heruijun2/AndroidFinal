package com.example.c01;

/**
 * Created by heruijun on 2017/9/10.
 */

public class Printer implements Runnable {

    private String message;

    public Printer(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.print(message);
        }
    }
}
