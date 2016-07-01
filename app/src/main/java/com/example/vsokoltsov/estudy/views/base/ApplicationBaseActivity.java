package com.example.vsokoltsov.estudy.views.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.vsokoltsov.estudy.R;

/**
 * Created by vsokoltsov on 22.03.16.
 */
public class ApplicationBaseActivity extends ActionBarActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showProgress(int msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            dismissProgress();
        }

        mProgressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), getResources().getString(msg));
    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}