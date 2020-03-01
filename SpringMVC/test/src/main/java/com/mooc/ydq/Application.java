package com.mooc.ydq;

import com.mooc.ydq.starter.MiniApplication;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //第一个参数为当前入口类，第二个为当前入口类的参数数组
        MiniApplication.run(Application.class, args);
    }
}
