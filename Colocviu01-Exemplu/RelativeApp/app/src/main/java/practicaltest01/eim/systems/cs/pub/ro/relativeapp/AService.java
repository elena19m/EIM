package practicaltest01.eim.systems.cs.pub.ro.relativeapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AService extends Service {
    public AService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int num1 = intent.getIntExtra("num1", 0);
        int num2 = intent.getIntExtra("num2", 0);
        String s = intent.getStringExtra("s");
        ProcessingThread p  = new ProcessingThread(this, num1, num2, s);
        p.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
