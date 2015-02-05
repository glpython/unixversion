package com.ysbz.main;

public class Main {

    public static void main(String args[]){

        long delay = 1000*60;
        long period = 1000*60;

        java.util.Timer timer = new java.util.Timer(false);
        //timer.schedule(new Task(), delay);
        //启动定时任务，从现在起过两秒以后，每隔五秒执行壹次
        timer.schedule(new Task(), delay, period*5);

    }
}
