package org.toudonou;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(10, "https://en.wikipedia.org/wiki/Main_Page", 1000);

        long time = System.currentTimeMillis();
        scheduler.start();
        time = System.currentTimeMillis() - time;
        System.out.println("Time: " + (time / 1000) + "s");
    }
}