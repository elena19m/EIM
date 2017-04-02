package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;


public class PhoneDialerActivity extends AppCompatActivity {

    Button star;
    Button diez;
    Button[] numbers = new Button[10];
    ImageButton back;
    ImageButton call;
    ImageButton close;
    ImageButton contacts;
    EditText phoneNumber;

    public class OnClickButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == star.getId()) {
                phoneNumber.setText(phoneNumber.getText().toString() + "*");
            }
            if (v.getId() == diez.getId()) {
                phoneNumber.setText(phoneNumber.getText().toString() + "#");
            }
            for (int i = 0; i < 10; i ++) {
                if (v.getId() == numbers[i].getId()) {
                    phoneNumber.setText(phoneNumber.getText().toString() + i);
                }
            }
            if (v.getId() == back.getId()) {
                String s = phoneNumber.getText().toString();
                if(!s.equals("")) {
                    s = s.substring(0, s.length() - 1);
                    phoneNumber.setText(s);
                }
            }
            if (v.getId() == call.getId()) {
                if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            PhoneDialerActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneNumber.getText().toString()));
                    startActivity(intent);
                }
            }

            if (v.getId() == close.getId()) {
                finish();
            }

            if (v.getId() == contacts.getId()) {
                String phone = phoneNumber.getText().toString();
                if (phone.length() > 0) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                    intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phone);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    OnClickButtonListener listen = new OnClickButtonListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_phone_dialer);

        star = (Button) findViewById(R.id.button10);
        diez = (Button) findViewById(R.id.button12);
        numbers[0] = (Button) findViewById(R.id.button11);
        numbers[1] = (Button) findViewById(R.id.button1);
        numbers[2] = (Button) findViewById(R.id.button2);
        numbers[3] = (Button) findViewById(R.id.button3);
        numbers[4] = (Button) findViewById(R.id.button4);
        numbers[5] = (Button) findViewById(R.id.button5);
        numbers[6] = (Button) findViewById(R.id.button6);
        numbers[7] = (Button) findViewById(R.id.button7);
        numbers[8] = (Button) findViewById(R.id.button8);
        numbers[9] = (Button) findViewById(R.id.button9);
        call = (ImageButton) findViewById(R.id.call);
        close = (ImageButton) findViewById(R.id.close);
        back = (ImageButton) findViewById(R.id.back);
        contacts = (ImageButton) findViewById(R.id.contacts);
        star.setOnClickListener(listen);
        diez.setOnClickListener(listen);
        for(int i =0; i < 10; i++)
            numbers[i].setOnClickListener(listen);
        call.setOnClickListener(listen);
        close.setOnClickListener(listen);
        back.setOnClickListener(listen);
        contacts.setOnClickListener(listen);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
    }
}


/*
public class PhoneDialerActivity extends AppCompatActivity {

    private EditText phoneNumberEditText = null;

    private NumberButtonClickListener numberButtonClickListener = new NumberButtonClickListener();
    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private CallButtonClickListener callButtonClickListener = new CallButtonClickListener();
    private HangupButtonClickListener hangupButtonClickListener = new HangupButtonClickListener();

    private class NumberButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());
        }
    }

    private class BackspaceButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                phoneNumberEditText.setText(phoneNumber);
            }
        }
    }

    private class CallButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    private class HangupButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            phoneNumberEditText.setText("");
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        phoneNumberEditText = (EditText)findViewById(R.id.phoneNumber);
        Button button;
        for (int index = 0; index < Constants.buttonIds.length; index++) {
            button = (Button)findViewById(Constants.buttonIds[index]);
            button.setOnClickListener(numberButtonClickListener);
        }

        ImageButton backspaceImageButton = (ImageButton)findViewById(R.id.back);
        backspaceImageButton.setOnClickListener(backspaceButtonClickListener);
        ImageButton callImageButton = (ImageButton)findViewById(R.id.call);
        callImageButton.setOnClickListener(callButtonClickListener);
        ImageButton hangupImageButton = (ImageButton)findViewById(R.id.close);
        hangupImageButton.setOnClickListener(hangupButtonClickListener);
    }


}
*/