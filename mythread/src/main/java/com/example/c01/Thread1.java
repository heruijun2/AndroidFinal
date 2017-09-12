package com.example.c01;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by heruijun on 2017/9/10.
 */

public class Thread1 {

    public static void main(String[] args) {
        // 隐藏了通过new创建Thread实例
        ThreadFactory factory = Executors.defaultThreadFactory();
        factory.newThread(new Printer("Nice!")).start();
        for (int i = 0; i < 10000; i++) {
            System.out.print("Good!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
