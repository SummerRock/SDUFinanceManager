package com.sdu.financesoft.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    GridView gvInfo;
    int[] images = new int[]{R.drawable.addoutaccount,
            R.drawable.addinaccount, R.drawable.outaccountinfo,
            R.drawable.inaccountinfo, R.drawable.showinfo, R.drawable.sysset,
            R.drawable.accountflag, R.drawable.exit};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] titles = this.getResources().getStringArray(R.array.main);

        gvInfo = (GridView) findViewById(R.id.gvInfo);
        pictureAdapter adapter = new pictureAdapter(titles, images, this);
        gvInfo.setAdapter(adapter);
        gvInfo.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = null;
                switch (arg2) {
                    case 0:
                        intent = new Intent(MainActivity.this, AddOutaccount.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, AddInaccount.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, Outaccountinfo.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, Inaccountinfo.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, Showinfo.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, Sysset.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, Accountflag.class);
                        startActivity(intent);
                        break;
                    case 7:
                        Toast.makeText(MainActivity.this, R.string.thankinfo,
                                Toast.LENGTH_SHORT).show();
                        finish();
                }
            }
        });
    }

    class pictureAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Picture> pictures;

        public pictureAdapter(String[] titles, int[] images, Context context) {
            super();
            pictures = new ArrayList<Picture>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < images.length; i++) {
                Picture picture = new Picture(titles[i], images[i]);
                pictures.add(picture);
            }
        }

        @Override
        public int getCount() {
            if (null != pictures) {
                return pictures.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int arg0) {
            return pictures.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ViewHolder viewHolder;
            if (arg1 == null) {
                arg1 = inflater.inflate(R.layout.gvitem, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);
                viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);
                arg1.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) arg1.getTag();
            }
            viewHolder.title.setText(pictures.get(arg0).getTitle());
            viewHolder.image.setImageResource(pictures.get(arg0).getImageId());
            return arg1;
        }
    }

    class ViewHolder {
        public TextView title;
        public ImageView image;
    }

    class Picture {
        private String title;
        private int imageId;

        public Picture() {
            super();
        }

        public Picture(String title, int imageId) {
            super();
            this.title = title;
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImageId() {
            return imageId;
        }

        public void setimageId(int imageId) {
            this.imageId = imageId;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == R.id.action_version) {
            String versionintro = this.getResources().getString(R.string.versionintroduce);
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setIcon(R.drawable.copyright);
            alert.setTitle(R.string.aboutversion);
            alert.setMessage(versionintro);
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}