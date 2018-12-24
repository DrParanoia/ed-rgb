package com.bmc.elite;

public class Main {
    public static void main(String[] args) {
        new Thread(new EliteProcessWatcherRunnable()).start();
    }
}
