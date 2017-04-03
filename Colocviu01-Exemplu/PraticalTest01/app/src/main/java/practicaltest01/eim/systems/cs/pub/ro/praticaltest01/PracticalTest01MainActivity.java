package practicaltest01.eim.systems.cs.pub.ro.praticaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {
    private EditText left_edit;
    private EditText right_edit;

    Button button;
    Button left;
    Button right;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.left_button:
                    int leftNumberOfClicks = Integer.parseInt(left_edit.getText().toString());
                    leftNumberOfClicks++;
                    left_edit.setText(String.valueOf(leftNumberOfClicks));
                    break;
                case R.id.right_button:
                    int rightNumberOfClicks = Integer.parseInt(right_edit.getText().toString());
                    rightNumberOfClicks++;
                    right_edit.setText(String.valueOf(rightNumberOfClicks));
                    break;
                case R.id.button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    int numberOfClicks = Integer.parseInt(left_edit.getText().toString()) +
                            Integer.parseInt(right_edit.getText().toString());
                    intent.putExtra("numberOfClicks", numberOfClicks);
                    startActivityForResult(intent, 1);
                    break;
            }
        }
    }

    ButtonClickListener l = new ButtonClickListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        left_edit = (EditText) findViewById(R.id.left_edit_text);
        right_edit = (EditText) findViewById(R.id.right_edit_text);

        button = (Button) findViewById(R.id.button);
        left = (Button) findViewById(R.id.left_button);
        right = (Button) findViewById(R.id.right_button);

        button.setOnClickListener(l);
        left.setOnClickListener(l);
        right.setOnClickListener(l);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("leftCount")) {
                left_edit.setText(savedInstanceState.getString("leftCount"));
            } else {
                left_edit.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("rightCount")) {
                right_edit.setText(savedInstanceState.getString("rightCount"));
            } else {
                right_edit.setText(String.valueOf(0));
            }
        } else {
            left_edit.setText(String.valueOf(0));
            right_edit.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("leftCount", left_edit.getText().toString());
        savedInstanceState.putString("rightCount", right_edit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("leftCount")) {
            left_edit.setText(savedInstanceState.getString("leftCount"));
        } else {
            left_edit.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightCount")) {
            right_edit.setText(savedInstanceState.getString("rightCount"));
        } else {
            right_edit.setText(String.valueOf(0));
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

}
