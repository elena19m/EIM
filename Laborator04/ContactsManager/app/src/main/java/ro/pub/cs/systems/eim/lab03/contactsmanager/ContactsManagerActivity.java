package ro.pub.cs.systems.eim.lab03.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Website;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    Button show;
    EditText name;
    EditText phone;
    EditText address;
    EditText email;
    Button save;
    Button cancel;

    LinearLayout to_show;
    EditText job;
    EditText company;
    EditText website;
    EditText im;

    class ButtonListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            if (v.getId() == show.getId()) {
                if(show.getText().toString().contains("Show")) {
                    show.setText("Hide Additional Field");
                    to_show.setVisibility(View.VISIBLE);
                } else {
                    show.setText("Show Additional Field");
                    to_show.setVisibility(View.INVISIBLE);
                }

            } else if (v.getId() == save.getId()) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name.toString());
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.toString());
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.toString());
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.toString());
                }
                if (job != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.toString());
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.toString());
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(Website.URL, String.valueOf(website));
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im.getText().toString());
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            } else if (v.getId() == cancel.getId()) {
                finish();
            }
        }
    }

    ButtonListener l = new ButtonListener();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        show = (Button) findViewById(R.id.show_hide);
        show.setOnClickListener(l);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(l);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(l);

        to_show = (LinearLayout) findViewById(R.id.layout_show);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phoneNumber);
        address = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);
        job = (EditText) findViewById(R.id.job);
        company = (EditText) findViewById(R.id.company);
        website = (EditText) findViewById(R.id.website);
        im = (EditText) findViewById(R.id.im);
    }
}
