package com.dongyangbike.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dongyangbike.app.R;
import com.dongyangbike.app.base.ApiConstant;
import com.dongyangbike.app.http.ack.BaseAck;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import tech.gujin.toast.ToastUtil;

public class RegisterActivity extends BaseActivity {

    private EditText mPhoneEt;
    private TextView mError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPhoneEt = findViewById(R.id.phone_et);
        mError = findViewById(R.id.error);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPhoneEt.getEditableText().toString().length() == 11) {
                    mError.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, Register2Activity.class);
                    intent.putExtra("phone", mPhoneEt.getEditableText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    mError.setVisibility(View.VISIBLE);
                }
            }
        });

        TextView login = findViewById(R.id.login);
        SpannableStringBuilder spannable = new SpannableStringBuilder(login.getText().toString());
        ForegroundColorSpan black = new ForegroundColorSpan(getResources().getColor(R.color.meta_text_primary));
        ForegroundColorSpan green = new ForegroundColorSpan(getResources().getColor(R.color.mainColor));
        spannable.setSpan(black, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(green, 7, login.length() - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        login.setText(spannable);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
