package com.dongyangbike.app.fragment;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.dongyangbike.app.dialog.CustomProgressDialog;

public class BaseFragment extends Fragment {
    CustomProgressDialog progressDialog;

    public void startProgressDialog(String text) {
        try {
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog.createDialog(getActivity());
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
