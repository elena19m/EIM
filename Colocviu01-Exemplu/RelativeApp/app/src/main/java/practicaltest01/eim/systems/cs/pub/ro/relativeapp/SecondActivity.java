package practicaltest01.eim.systems.cs.pub.ro.relativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    TextView text;
    Button b1, b2;


    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.button:
                    setResult(RESULT_OK);
                    break;
                case R.id.button2:
                    setResult(RESULT_CANCELED);
                    break;

            }
            finish();

        }
    }

    ButtonClickListener l = new ButtonClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        text = (TextView) findViewById(R.id.text);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(l);
        b2.setOnClickListener(l);

        Intent intentFromParent = getIntent();
        Bundle data = intentFromParent.getExtras();

        text.setText("Hello from the otter app, " + data.getString("from us"));
    }

}
