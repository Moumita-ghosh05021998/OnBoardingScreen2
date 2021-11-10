package com.example.onboardingscreen2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFamily extends AppCompatActivity {
    EditText txtmessage,txtnumber;
    Button sendMessageManually,sendSms,chooseNumber;
    private static final  int RESULT_PICK_CONTACT =1;
    String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_family);

        txtmessage =findViewById(R.id.txtmessage);
        txtnumber=findViewById(R.id.txtnumber);
        chooseNumber=findViewById(R.id.chooseNumber);
        sendMessageManually=findViewById(R.id.sendMessageManually);
        sendSms = findViewById(R.id.sendSms);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();

        sendMessageManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtnumber.equals("") || txtnumber.equals(null)||txtnumber.length()<10) {
                    txtnumber.requestFocus();
                    txtnumber.setError("Enter a right mobile number");
                }
                else{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(txtnumber.getText().toString(), null, "I need help! \n It's urgent.\n Please contact me immediately", null, null);
                    Toast.makeText(MessageFamily.this, "Sms Send", Toast.LENGTH_SHORT).show();
                }

            }
        });
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtnumber.equals("") || txtnumber.equals(null)||txtnumber.length()<10 ) {
                    txtnumber.setError("Enter a right mobile number");
                }
                else {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(txtnumber.getText().toString(), null, txtmessage.getText().toString(), null, null);
                    Toast.makeText(MessageFamily.this, "Sms Send", Toast.LENGTH_SHORT).show();
                }

            }
        });

        chooseNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(in,RESULT_PICK_CONTACT);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch(requestCode){
                case RESULT_PICK_CONTACT :
                    contactPicked(data);
                    break;
            }
        }else{
            Toast.makeText(this, "Failed to picked contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor= null;
        try{
            String phoneNo= null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo =cursor.getString(phoneIndex);
            txtnumber.setText(phoneNo);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}