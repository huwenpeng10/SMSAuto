package com.keen.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KeenWong on 2015-12-10.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

    /**
     * 短信验证码内容
     */
    private String strSMSContent;
    /**
     * 验证码长度为4
     */
    private static final int CODE_LEN = 4;

    private SMSInteraction smsInteraction;

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for (Object obj : objs) {
            byte[] pdu = (byte[]) obj;
            SmsMessage sms = SmsMessage.createFromPdu(pdu);
            // 短信的内容
            String message = sms.getMessageBody();
            String from = sms.getOriginatingAddress();
            strSMSContent = from + "   " + message;
            if (!TextUtils.isEmpty(from)) {
                String code = patternCode(message);
                if (!TextUtils.isEmpty(code)) {
                    strSMSContent = code;
                    if (smsInteraction != null)
                    {
                        smsInteraction.setCodeValue(strSMSContent);
                    }
                }
            }
        }
    }

    /**
     * 匹配短信中间的验证码
     *
     * @param message
     * @return
     */
    private String patternCode(String message) {
        /* 正则匹配验证码 */
        String patternCoder = "(?<!\\d)\\d{" + CODE_LEN + "}(?!\\d)";
        if (TextUtils.isEmpty(message)) {
            return null;
        }
        Pattern p = Pattern.compile(patternCoder);
        Matcher matcher = p.matcher(message);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    public void setSMSInteractionListener(SMSInteraction smsInteraction) {
        this.smsInteraction = smsInteraction;
    }
}
