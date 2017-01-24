package com.sdu.financesoft.activity;

import java.util.Calendar;

import com.sdu.financesoft.activity.AddInaccount;
import com.sdu.financesoft.activity.R;
import com.sdu.financesoft.dao.InaccountDAO;
import com.sdu.financesoft.model.Tb_inaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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

public class AddInaccount extends Activity {
    EditText txtInMoney, txtInHandler, txtInMark;// 创建4个EditText对象
    TextView txtInTime;
    Spinner spInType;// 创建Spinner对象
    Button btnInSaveButton;// 创建Button对象“保存”
    Button btnInCancelButton;// 创建Button对象“取消”

    Calendar c = Calendar.getInstance();// 获取当前系统日期
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);// 设置布局文件
        txtInMoney = (EditText) findViewById(R.id.txtInMoney);// 获取金额文本框
        txtInTime = (TextView) findViewById(R.id.txtInTime);// 获取时间文本框
        txtInHandler = (EditText) findViewById(R.id.txtInHandler);// 获取来源文本框
        txtInHandler.setHint(R.string.addincomesource);
        txtInMark = (EditText) findViewById(R.id.txtInMark);// 获取备注文本框
        txtInMark.setHint(R.string.addremark);
        spInType = (Spinner) findViewById(R.id.spInType);// 获取类别下拉列表
        btnInSaveButton = (Button) findViewById(R.id.btnInSave);// 获取保存按钮
        btnInCancelButton = (Button) findViewById(R.id.btnInCancel);// 获取取消按钮
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

        txtInTime.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddInaccount.this, new OnDateSetListener() {
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

        btnInSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String strInMoney = txtInMoney.getText().toString();// 获取金额文本框的值
                if (!strInMoney.isEmpty()) {// 判断金额不为空
                    // 创建InaccountDAO对象
                    InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
                    // 创建Tb_inaccount对象
                    Tb_inaccount tb_inaccount = new Tb_inaccount(
                            inaccountDAO.getMaxId() + 1, Double
                            .parseDouble(strInMoney), txtInTime
                            .getText().toString(), spInType
                            .getSelectedItem().toString(),
                            txtInHandler.getText().toString(),
                            txtInMark.getText().toString());
                    inaccountDAO.add(tb_inaccount);// 添加收入信息
                    // 弹出信息提示
                    Toast.makeText(AddInaccount.this, R.string.newdataaddsucceed,
                            Toast.LENGTH_SHORT).show();
                    txtInMoney.setText("");
                } else {
                    txtInMoney.setError("Please input figure!");
                }
            }
        });

        btnInCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                txtInMoney.setText("");// 设置金额文本框为空
                txtInMoney.setHint("0.00");// 为金额文本框设置提示
                updateDisplay();
                txtInHandler.setText("");// 设置来源文本框为空
                txtInMark.setText("");// 设置备注文本框为空
                spInType.setSelection(0);// 设置类别下拉列表默认选择第一项
            }
        });
    }

    private void updateDisplay() {
        // 显示设置的时间
        txtInTime.setText(new StringBuilder().append(mYear).append("-")
                .append(mMonth + 1).append("-").append(mDay));
    }
}