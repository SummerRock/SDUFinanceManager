package com.sdu.financesoft.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sdu.financesoft.view.LocusPassWordView;
import com.sdu.financesoft.view.LocusPassWordView.OnCompleteListener;
import com.sdu.financesoft.util.StringUtil;

public class SetPasswordActivity extends Activity {
    private LocusPassWordView lpwv;
    private String password;
    private boolean needverify = true;
    private Toast toast;
    private Button buttonSave, tvReset;

    private void showToast(CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
//			toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }

        toast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpassword_activity);

        buttonSave = (Button) this.findViewById(R.id.tvSave);
        tvReset = (Button) this.findViewById(R.id.tvReset);

        lpwv = (LocusPassWordView) this.findViewById(R.id.mLocusPassWordView);
        lpwv.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(String mPassword) {
                password = mPassword;
                if (needverify) {
                    if (lpwv.verifyPassword(mPassword)) {
                        showToast("Please set new password!");
                        buttonSave.setVisibility(View.VISIBLE);
                        tvReset.setVisibility(View.VISIBLE);
                        lpwv.clearPassword();
                        needverify = false;
                    } else {
                        showToast("Wrong password,please try again.");
                        lpwv.clearPassword();
                        password = "";
                    }
                }
            }
        });

        OnClickListener mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tvSave:
                        if (StringUtil.isNotEmpty(password)) {
                            if (lpwv.isPasswordEmpty()) {
                                startActivity(new Intent(SetPasswordActivity.this, Login.class));
                                showToast("Password set successful,please remember it.");
                                lpwv.resetPassWord(password);
                                lpwv.clearPassword();
                                finish();
                            } else {
                                if (lpwv.verifyPassword(password)) {
                                    showToast("Password no change!");
                                    finish();
                                } else {
                                    lpwv.resetPassWord(password);
                                    lpwv.clearPassword();
                                    showToast("Password modified successful,please remember it.");
                                    //startActivity(new Intent(SetPasswordActivity.this,Login.class));
                                    finish();
                                }
                            }
                        } else {
                            lpwv.clearPassword();
                            showToast("Password can not be null,please input password.");
                        }
                        break;
                    case R.id.tvReset:
                        lpwv.clearPassword();
                        break;
                }
            }
        };

        buttonSave.setOnClickListener(mOnClickListener);
        tvReset.setOnClickListener(mOnClickListener);
        if (lpwv.isPasswordEmpty()) {
            buttonSave.setVisibility(View.VISIBLE);
            tvReset.setVisibility(View.VISIBLE);
            this.needverify = false;
            showToast("Please input passoword");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
