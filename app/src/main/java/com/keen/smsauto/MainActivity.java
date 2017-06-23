package com.keen.smsauto;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.keen.broadcast.SMSBroadcastReceiver;
import com.keen.broadcast.SMSInteraction;

/**
 * Created by KeenWong on 2015-12-10.
 */
public class MainActivity extends Activity implements SMSInteraction, View.OnClickListener {

    private EditText etCode;
    private Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        regSMSReceiver();
    }


    /**
     * 初始化
     */
    private void initView() {
        etCode = (EditText) findViewById(R.id.et_code);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(this);
    }

    /**
     * 注册广播
     */
    private void regSMSReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);
        SMSBroadcastReceiver smsBR = new SMSBroadcastReceiver();
        registerReceiver(smsBR, filter);
        smsBR.setSMSInteractionListener(this);
    }

    /**
     * 更新验证码值
     *
     * @param content
     */
    @Override
    public void setCodeValue(String content) {
        if (etCode != null) {
            etCode.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skip:
                Intent i = new Intent(this, Main2Activity.class);
                startActivity(i);
                //finish();
                break;
            default:
                break;
        }
    }
}
