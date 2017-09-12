package com.example.c01;

/**
 * Created by heruijun on 2017/9/10.
 */

public class Bank {

    private int money;
    private String name;

    public Bank(String name, int money) {
        this.name = name;
        this.money = money;
    }

    // 存款
    public synchronized void deposit(int m) {
        money += m;
    }

    public synchronized boolean withdraw(int m) {
        if (money >= m) {
            money -= m;
            return true;    // 取款成功
        } else {
            return false;   // 余额不足
        }
    }

    public String getName() {
        return name;
    }
}
