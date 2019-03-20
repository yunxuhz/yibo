package com.dongyangbike.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.dongyangbike.app.R;

import tech.gujin.toast.ToastUtil;

public class FeedbackActivity extends BaseActivity {

    private int mIndex = -1;

    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private CheckBox mCheckBox5;

    private EditText mEdittext;
    private TextView mSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);

        mCheckBox1 = findViewById(R.id.box1);
        mCheckBox2 = findViewById(R.id.box2);
        mCheckBox3 = findViewById(R.id.box3);
        mCheckBox4 = findViewById(R.id.box4);
        mCheckBox5 = findViewById(R.id.box5);

        mEdittext = findViewById(R.id.feedback);
        mSend = findViewById(R.id.send);

        mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mIndex = 1;
                    setCheckedFalse();
                    mCheckBox1.setChecked(true);
                } else {
                    mIndex = -1;
                    setCheckedFalse();
                }
            }
        });

        mCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mIndex = 2;
                    setCheckedFalse();
                    mCheckBox2.setChecked(true);
                } else {
                    mIndex = -1;
                    setCheckedFalse();
                }
            }
        });

        mCheckBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mIndex = 3;
                    setCheckedFalse();
                    mCheckBox3.setChecked(true);
                } else {
                    mIndex = -1;
                    setCheckedFalse();
                }
            }
        });

        mCheckBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mIndex = 4;
                    setCheckedFalse();
                    mCheckBox4.setChecked(true);
                } else {
                    mIndex = -1;
                    setCheckedFalse();
                }
            }
        });

        mCheckBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mIndex = 5;
                    setCheckedFalse();
                    mCheckBox5.setChecked(true);
                } else {
                    mIndex = -1;
                    setCheckedFalse();
                }
            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });
    }

    private void setCheckedFalse() {
        mCheckBox1.setChecked(false);
        mCheckBox2.setChecked(false);
        mCheckBox3.setChecked(false);
        mCheckBox4.setChecked(false);
        mCheckBox5.setChecked(false);
    }

    private void sendFeedback() {
        if(mIndex != -1) {
            if(mIndex == 5) {
                ToastUtil.show("已反馈，谢谢！");
            } else {
                ToastUtil.show("已反馈，谢谢！");
            }
        } else {
            ToastUtil.show("请选择");
        }
    }
}
