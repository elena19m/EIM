package practicaltest01.eim.systems.cs.pub.ro.relativeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button lt,lb,c,rt,rb;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("crt_time"));
            Log.d("[Message Artm:]", intent.getStringExtra("arith"));
            Log.d("[Message Geom]", intent.getStringExtra("geom"));
            Log.d("[Message S]", intent.getStringExtra("str"));

        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent;
            switch(view.getId()) {
                case R.id.lt:
                    Toast.makeText(getApplicationContext(), "clicked on lt", Toast.LENGTH_LONG).show();

                    intent = new Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.SecondActivity");
                    intent.putExtra("from us", "We send you greetings from lt");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.lb:
                    Toast.makeText(getApplicationContext(), "clicked on lb", Toast.LENGTH_LONG).show();

                    intent = new Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.SecondActivity");
                    intent.putExtra("from us", "We send you greetings from lb");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.rt:
                    Toast.makeText(getApplicationContext(), "clicked on rt", Toast.LENGTH_LONG).show();

                    intent = new Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.SecondActivity");
                    intent.putExtra("from us", "We send you greetings from rt");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.rb:
                    Toast.makeText(getApplicationContext(), "clicked on rb", Toast.LENGTH_LONG).show();

                    intent = new Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.SecondActivity");
                    intent.putExtra("from us", "We send you greetings from rb");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.c:
                    Toast.makeText(getApplicationContext(), "clicked on c", Toast.LENGTH_LONG).show();

                    intent = new Intent("ro.pub.cs.systems.eim.practicaltest01.intent.action.SecondActivity");
                    intent.putExtra("from us", "We send you greetings from c");
                    startActivityForResult(intent, 1);
                    break;


            }

        }
    }

    ButtonClickListener l = new ButtonClickListener();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lt = (Button) findViewById(R.id.lt);
        lb = (Button) findViewById(R.id.lb);
        rt = (Button) findViewById(R.id.rt);
        rb = (Button) findViewById(R.id.rb);
        c = (Button) findViewById(R.id.c);
        lt.setOnClickListener(l);
        lb.setOnClickListener(l);
        rt.setOnClickListener(l);
        rb.setOnClickListener(l);
        c.setOnClickListener(l);

        Intent intent = new Intent(getApplicationContext(), AService.class);
        intent.putExtra("num1", 3);
        intent.putExtra("num2", 40);
        intent.putExtra("s", "Hello from the otter app!!!");
        getApplicationContext().startService(intent);


        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Toast.makeText(getApplicationContext(), "It's fiiine", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Abord mission. It's not fine. Abord Mission", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, AService.class);
        stopService(intent);
        super.onDestroy();
    }
}
