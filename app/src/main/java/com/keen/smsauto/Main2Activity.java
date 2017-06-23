package com.keen.smsauto;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.keen.broadcast.SMSBroadcastReceiver;
import com.keen.broadcast.SMSInteraction;

public class Main2Activity extends AppCompatActivity implements SMSInteraction, View.OnClickListener {

    private EditText etCode;
    private Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        regSMSReceiver();
    }

    private void regSMSReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);
        SMSBroadcastReceiver smsBR = new SMSBroadcastReceiver();
        registerReceiver(smsBR, filter);
        smsBR.setSMSInteractionListener(this);
    }

    private void initView() {
        etCode = (EditText) findViewById(R.id.et_code);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
    }

    public void setCodeValue(String content) {
        if (etCode != null) {
            etCode.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skip:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                //finish();
                break;
            default:
                break;
        }
    }
}
