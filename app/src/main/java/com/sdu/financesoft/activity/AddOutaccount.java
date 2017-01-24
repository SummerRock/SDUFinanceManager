package com.sdu.financesoft.activity;

import java.util.Calendar;

import com.sdu.financesoft.dao.OutaccountDAO;
import com.sdu.financesoft.model.Tb_outaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class AddOutaccount extends Activity {
    EditText txtMoney, txtAddress, txtMark;
    TextView txtOutTime;
    Spinner spType;// 创建Spinner对象
    Button btnSaveButton;// 创建Button对象“保存”
    Button btnCancelButton;// 创建Button对象“取消”

    Calendar c = Calendar.getInstance();// 获取当前系统日期
    private int mYear;// 年
    private int mMonth;// 月
    private int mDay;// 日

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addoutaccount);// 设置布局文件
        txtMoney = (EditText) findViewById(R.id.txtMoney);// 获取金额文本框
        txtOutTime = (TextView) findViewById(R.id.txtTime);// 获取时间文本框
        txtAddress = (EditText) findViewById(R.id.txtAddress);// 获取地点文本框
        txtAddress.setHint(R.string.addposition);
        txtMark = (EditText) findViewById(R.id.txtMark);// 获取备注文本框
        txtMark.setHint(R.string.addremark);
        spType = (Spinner) findViewById(R.id.spType);// 获取类别下拉列表
        btnSaveButton = (Button) findViewById(R.id.btnSave);// 获取保存按钮
        btnCancelButton = (Button) findViewById(R.id.btnCancel);// 获取取消按钮
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

        txtOutTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddOutaccount.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        updateDisplay();// 显示设置的日期
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String strMoney = txtMoney.getText().toString();// 获取金额文本框的值
                if (!strMoney.isEmpty()) {// 判断金额不为空
                    // 创建OutaccountDAO对象
                    OutaccountDAO outaccountDAO = new OutaccountDAO(
                            AddOutaccount.this);
                    // 创建Tb_outaccount对象
                    Tb_outaccount tb_outaccount = new Tb_outaccount(
                            outaccountDAO.getMaxId() + 1, Double
                            .parseDouble(strMoney), txtOutTime
                            .getText().toString(), spType
                            .getSelectedItem().toString(),
                            txtAddress.getText().toString(), txtMark
                            .getText().toString());
                    outaccountDAO.add(tb_outaccount);// 添加支出信息
                    // 弹出信息提示
                    Toast.makeText(AddOutaccount.this, R.string.newdataaddsucceed,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddOutaccount.this, R.string.addfigure,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                txtMoney.setText("");// 设置金额文本框为空
                txtMoney.setHint("0.00");// 为金额文本框设置提示
                updateDisplay();
                txtAddress.setText("");// 设置地点文本框为空
                txtMark.setText("");// 设置备注文本框为空
                spType.setSelection(0);// 设置类别下拉列表默认选择第一项
            }
        });
    }

    private void updateDisplay() {
        // 显示设置的时间
        txtOutTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }
}
