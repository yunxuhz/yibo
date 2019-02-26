package com.dongyangbike.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dongyangbike.app.dialog.CustomProgressDialog;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CustomProgressDialog progressDialog;

    public void startProgressDialog(String text) {
        try {
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog.createDialog(this);
                progressDialog.setMessage(text);
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭进度条
     */

    public void stopProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != progressDialog) {
                progressDialog.dismiss();
            }
        }
        progressDialog = null;
    }
}
