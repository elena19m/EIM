package practicaltest01.eim.systems.cs.pub.ro.relativeapp;

import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.Random;

/**
 * Created by Elena Mihailescu on 03.04.2017.
 */
public class ProcessingThread extends Thread {
    int num1;
    int num2;
    float arithm;
    float geom;
    String s;
    Context c;

    boolean ok = true;

    ProcessingThread(Context c, int num1, int num2, String s) {
        this.num1 = num1;
        this.num2 = num2;
        this.s = s;
        this.c = c;
        arithm = (num1 + num2) /2 ;
        geom = (float) Math.sqrt(num1 * num2);
    }

    @Override
    public void run() {
        while(ok) {
            sendMessage();
            sleep();
        }
    }
    Random random = new Random();

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra("crt_time",  new Date(System.currentTimeMillis()) + "");
        intent.putExtra("arith", arithm + "");
        intent.putExtra("geom", geom + "");
        intent.putExtra("str", s);
        c.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        ok = false;
    }
}
