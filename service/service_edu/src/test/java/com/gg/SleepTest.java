package com.gg;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SleepTest {
    public static void main(String[] args) {
        new Thread(new Sleep1()).start();

        new Thread(new Sleep2()).start();
    }

    public static class Sleep1 implements Runnable{
        /**
         * 实现体育老师安排体育课代表进行点名
         */
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //这个步骤我们可以想象成锁住的是一节体育课
            synchronized (SleepTest.class) {
                System.out.println("体育老师安排体育委员进行点名");
                try {
                    //体育老师开始看手表，准备上课
                    System.out.println("体育老师开始等待时间:"+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
                    //体育老师在楼下等待,然后玩一会手机，休息1000L时间
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch  block
                    e.printStackTrace();
                }
                System.out.println("体育老师结束等待时间:"+new  SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new  Date()));
                //System.out.println("体育老师得到点名的结果开始上课");
                System.out.println("体育老师去操场开始上课");
            }
        }

    }

    public static class Sleep2 implements Runnable{

        /**
         * 实现体育课代表进行队列点名
         */
        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (SleepTest.class) {
                System.out.println("体育委员进行点名");

                try {
                    //点名需要花费的时间
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch  block
                    e.printStackTrace();
                }
                System.out.println("体育委员点名结束");
                //通知体育老师点名结束了
                //WaitTest.class.notify();

                System.out.println("体育课代表带人去操场");
            }
        }
    }

}