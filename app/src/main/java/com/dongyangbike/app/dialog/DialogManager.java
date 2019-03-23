package com.dongyangbike.app.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.dongyangbike.app.R;


/**
 * Created by Administrator on 2017/12/1.
 */

public class DialogManager {

    /**
     * alert弹窗（只带内容和确定按钮，不含Title）
     *
     * @param context
     * @param content
     * @param listener
     */
    public static void showDialog(Context context, String content,
                                  final DialogClickListener listener) {

        final ConfirmTitleDialog confirmTitleDialog = new ConfirmTitleDialog(context, content,
                "确认", "取消");

        confirmTitleDialog.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTitleDialog.dismiss();
                listener.onRightClick();
            }
        });

        confirmTitleDialog.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTitleDialog.dismiss();
                listener.onLeftClick();
            }
        });
        confirmTitleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss();

            }
        });
        confirmTitleDialog.show();
    }

    public static void showDialog(Context context, String content,String leftText,String rightText,
                                  final DialogClickListener listener) {

        final ConfirmTitleDialog confirmTitleDialog = new ConfirmTitleDialog(context, content,
                leftText, rightText);
        confirmTitleDialog.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTitleDialog.dismiss();
                listener.onRightClick();
            }
        });

        confirmTitleDialog.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTitleDialog.dismiss();
                listener.onLeftClick();
            }
        });
        confirmTitleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss();

            }
        });
        confirmTitleDialog.show();
    }

    public static void showCustomDialog(Context context, int layoutId, String leftText,String rightText,
                                  final DialogClickListener listener) {

        final ConfirmTitleDialog confirmTitleDialog = new ConfirmTitleDialog(context, layoutId,
                leftText, rightText);
        confirmTitleDialog.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTitleDialog.dismiss();
                listener.onRightClick();
            }
        });

        confirmTitleDialog.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTitleDialog.dismiss();
                listener.onLeftClick();
            }
        });
        confirmTitleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss();

            }
        });
        confirmTitleDialog.show();
    }
}
