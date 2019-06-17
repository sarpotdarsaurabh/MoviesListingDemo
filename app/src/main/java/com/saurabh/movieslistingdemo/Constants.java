package com.saurabh.movieslistingdemo;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by saurabhs on 12/6/19.
 */

public class Constants {
    private Constants() {
    }
    public static final String TABLE_NAME_MOVIES ="movies";
    public static final String TABLE_NAME_GENRES ="genres";
    public static final String DB_NAME ="moviesDb.db";
    public static boolean isOfflineModeEnabled=false;
    static ProgressDialog dialog = null;

    public static void showProgressDialog(Context context, boolean isShow, String DispMsg) {

        try {
            if (dialog == null && isShow) {
                dialog = new ProgressDialog(context);
                dialog.setMessage(DispMsg);
                dialog.setCancelable(false);
                dialog.setMax(100);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            } else {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
