package com.sdu.financesoft.activity;

import java.util.List;

import com.sdu.financesoft.dao.OutaccountDAO;
import com.sdu.financesoft.model.Tb_outaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Outaccountinfo extends Activity {
    public static final String FLAG = "id";
    ListView lvinfo;
    String strType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outaccountinfo);
        lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);

        ShowInfo();

        lvinfo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String strInfo = String.valueOf(((TextView) view).getText());
                String strid = strInfo.substring(0, strInfo.indexOf('|'));
                Intent intent = new Intent(Outaccountinfo.this,
                        InfoManage.class);
                intent.putExtra(FLAG, new String[]{strid, strType});
                startActivity(intent);
            }
        });
    }

    private void ShowInfo() {
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;
        strType = "btnoutinfo";
        OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this);
        List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
                (int) outaccountinfo.getCount());
        strInfos = new String[listoutinfos.size()];
        int i = 0;
        for (Tb_outaccount tb_outaccount : listoutinfos) {
            strInfos[i] = tb_outaccount.getid() + "|" + tb_outaccount.getType()
                    + " " + String.valueOf(tb_outaccount.getMoney()) + "元     "
                    + tb_outaccount.getTime();
            i++;
        }
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strInfos);
        lvinfo.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        ShowInfo();
    }
}