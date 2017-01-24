package com.sdu.financesoft.activity;

import com.sdu.financesoft.dao.InaccountDAO;
import com.sdu.financesoft.dao.OutaccountDAO;
import com.sdu.financesoft.model.Tb_pwd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

public class Sysset extends ListActivity {
    // private static final int SERIES_NR = 2;
    /**
     * Called when the activity is first created.
     */
    private ArrayList<Map<String, String>> maps = new ArrayList<Map<String, String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.main);
        // 加入 ListItem “ 调度查询 ”
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", " 柱状图 ");
        map.put("desc", " 最近7天支出收入情况柱状图 ");
        maps.add(map);
        map = new HashMap<String, String>();
        map.put("name", " 饼状图 ");
        map.put("desc", " 各类支出所占比例饼状图 ");
        maps.add(map);
        map = new HashMap<String, String>();
        map.put("name", " 修改密码 ");
        map.put("desc", " 修改系统登录密码 ");
        maps.add(map);
        // 构建 listView 的适配器
        // SDK 库中提供的一个包含两个 TextView 的 layout new String[]{ "name" , "desc" },
        // maps 中的 key
        SimpleAdapter adapter = new SimpleAdapter(this, maps,
                android.R.layout.simple_list_item_2, new String[]{"name",
                "desc"}, new int[]{android.R.id.text1,
                android.R.id.text2});
        // 两个 TextView 的 id );
        this.setListAdapter(adapter);
    }

    // ListItem 监听器方法
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = null;
        switch (position) {
            case 0:
                XYMultipleSeriesRenderer renderer = getBarDemoRenderer();
                intent = ChartFactory.getBarChartIntent(this, getBarDemoDataset(),
                        renderer, Type.DEFAULT);
                startActivity(intent);
                break;
            case 1:
                intent = execute(Sysset.this);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(Sysset.this, SetPasswordActivity.class);
                startActivity(intent);
                break;
        /*case 3:
			// 创建OutaccountDAO对象
			OutaccountDAO outaccountDAO = new OutaccountDAO(Sysset.this);
			double a = outaccountDAO.getOneDayMoney(4);
			Toast.makeText(getBaseContext(), "Oneday = " + a,
					Toast.LENGTH_SHORT).show();
			break;*/
        }
		/*
		 * if(position == 0){ XYMultipleSeriesRenderer renderer =
		 * getBarDemoRenderer(); Intent intent =
		 * ChartFactory.getBarChartIntent(this, getBarDemoDataset(), renderer,
		 * Type.DEFAULT); startActivity(intent); }else if(position == 1) {
		 * Intent intent = execute(Sysset.this); startActivity(intent); }
		 */
    }

    // 设置饼状图
    public Intent execute(Context context) {
        // 创建OutaccountDAO对象
        OutaccountDAO outaccountDAO = new OutaccountDAO(Sysset.this);
        double foodmoney = outaccountDAO.getAllFoodMoney();
        double studymoney = outaccountDAO.getAllStudyMoney();
        double Ishoppingmoney = outaccountDAO.getAllIShoppingMoney();
        double trafficmoney = outaccountDAO.getAllTrafficMoney();
        double othermoney = outaccountDAO.getAllOtherMoney();
        double[] values = new double[]{foodmoney, studymoney, Ishoppingmoney,
                trafficmoney, othermoney};
        int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.MAGENTA,
                Color.YELLOW, Color.CYAN};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setChartTitle("各类支出所占比例饼状图");
        return ChartFactory.getPieChartIntent(context,
                buildCategoryDataset("Project budget", values), renderer,
                "饼状分析图");
    }

    // 设置饼状图的Renderer
    protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    // 设置饼状图的Series
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        int k = 0;
        String[] str = new String[]{"饮食", "学习", "网购", "出行", "其他"};
        for (double value : values) {
            series.add(str[k++], value);
        }

        return series;
    }

    // 设置柱状图的Series
    private XYMultipleSeriesDataset getBarDemoDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 7;
        // Random r = new Random();
        // for (int i = 0; i < SERIES_NR; i++) {
        // 创建OutaccountDAO对象
        OutaccountDAO outaccountDAO = new OutaccountDAO(Sysset.this);
        // 创建InaccountDAO对象
        InaccountDAO inaccountDAO = new InaccountDAO(Sysset.this);
        CategorySeries series = new CategorySeries("支出");
        for (int k = 0; k < nr; k++) {
            series.add(outaccountDAO.getOneDayMoney(0 - k));
        }
        dataset.addSeries(series.toXYSeries());
        CategorySeries series1 = new CategorySeries("收入");
        for (int k = 0; k < nr; k++) {
            series1.add(inaccountDAO.getOneDayInMoney(0 - k));
        }
        dataset.addSeries(series1.toXYSeries());
		/*
		 * CategorySeries series2 = new CategorySeries("差额"); for (int k = 0; k
		 * < nr; k++) { series2.add(outaccountDAO.getOneDayMoney(0-k)); }
		 * dataset.addSeries(series2.toXYSeries());
		 */
        // }
        return dataset;
    }

    // 设置柱状图的Renderer
    public XYMultipleSeriesRenderer getBarDemoRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.GREEN);
        renderer.addSeriesRenderer(r);
        // r = new SimpleSeriesRenderer();
        // r.setColor(Color.WHITE);
        // renderer.addSeriesRenderer(r);
        setChartSettings(renderer);
        return renderer;
    }

    // 设置柱状图Renderer的属性
    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setChartTitle("收支情况图");
        renderer.setXTitle("时间(单位：天)");
        renderer.setYTitle("金钱(单位：元)");
        renderer.setXAxisMin(0.5);// 设置X轴的最小值
        renderer.setXAxisMax(10.5);// 设置X轴的最大值
        renderer.setYAxisMin(0);// 设置Y轴的最小值
        renderer.setYAxisMax(800);// 设置Y轴最大值
        renderer.setDisplayChartValues(true); // 设置是否在柱体上方显示值
        renderer.setShowGrid(true);// 设置是否在图表中显示网格
        renderer.setXLabels(0);// 设置X轴显示的刻度标签的个数
        String[] strX = new String[]{"今天", "昨天", "前天", "3天前", "4天前", "5天前",
                "6天前"};
        for (int i = 1; i < 8; i++) {
            renderer.addTextLabel(i, strX[i - 1]);
        }
    }

}
