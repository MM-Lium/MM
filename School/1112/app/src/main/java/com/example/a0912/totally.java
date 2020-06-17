package com.example.a0912;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class totally extends Fragment {

    DBService myDb;
    TextView money, money2, money3;
    private static String TAG = "totally";
    PieChart pieChart;
    String[] chs;
    Float[] much;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Float> muchlist = new ArrayList<Float>();
    String stringValue;
    String stringValue2;
    String stringValue3;
    String dollar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_totally, container, false);
        Log.d(TAG, "onCreate: starting to create chart");

        myDb = new DBService(getActivity());
        pieChart = (PieChart) rootView.findViewById(R.id.idPieChart);
        money = rootView.findViewById(R.id.money);
        money2 = rootView.findViewById(R.id.money2);
        money3 = rootView.findViewById(R.id.money3);

        Cursor cursor = myDb.sum();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            stringValue = Integer.toString(cursor.getInt(0));
            money.setText(stringValue);
        }
        Cursor cursor3 = myDb.sum2();
        if (cursor3.getCount() != 0) {
            cursor3.moveToFirst();
            stringValue2 = Integer.toString(cursor3.getInt(0));
            money2.setText(stringValue2);
        }
        Cursor cursor4 = myDb.viewData();
        if (cursor4.getCount() != 0) {
            cursor4.moveToFirst();
            dollar = cursor4.getString(2);
        }
        Cursor cursor5 = myDb.sum3();
        if (cursor5.getCount() != 0) {
            cursor5.moveToFirst();
            stringValue3 = Integer.toString(cursor5.getInt(0));
            money3.setText(stringValue3);
        }

        //圓餅圖表外間距
        pieChart.setExtraOffsets(2, 0, 2, 0);
        //圓餅圖是否可以旋轉
        pieChart.setRotationEnabled(false);
        //轉的速度
        pieChart.setDragDecelerationFrictionCoef(0.5f);
        //圓餅圖上的文字
        pieChart.setDrawEntryLabels(false); //不顯示文字
        // 圓餅圖背景描述
        pieChart.setDescription("我的圖表");
        //圓餅圖內數字轉為百分比
        pieChart.setUsePercentValues(true);
        //圓餅圖中心
        pieChart.setDrawHoleEnabled(true);
        //圓餅圖中心半徑
        pieChart.setHoleRadius(25f);
        //圓餅圖中心顏色
        pieChart.setHoleColor(Color.WHITE);
        // 半透明圈半徑
        pieChart.setTransparentCircleRadius(31f);
        // 半透明圈顏色
        pieChart.setTransparentCircleColor(Color.WHITE);
        // 半透明圈透明度
        pieChart.setTransparentCircleAlpha(100);
        //圓餅圖中心文字
        pieChart.setCenterText("投資比例");
        //圓餅圖中心文字大小
        pieChart.setCenterTextSize(20);

        Cursor cursor2 = myDb.totallyT();
        chs = new String[cursor2.getCount()];
        much = new Float[cursor2.getCount()];
        if (cursor2.getCount() != 0) {
            cursor2.moveToFirst();
            for (int i = 0; i < cursor2.getCount(); i++) {
                String str = cursor2.getString(0);
                Float mm = cursor2.getFloat(1);
                chs[i] = str;
                much[i] = Float.valueOf(mm + "f");
                list.add(chs[i]);
                muchlist.add(much[i]);
                cursor2.moveToNext();
            }

            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {  // Entry, x: 0.0 y (sum): 44.32
                    Log.d(TAG, "onValueSelected: Value select from chart.");
                    Log.d(TAG, "onValueSelected: " + e.toString());
                    Log.d(TAG, "onValueSelected: " + h.toString());
                    int pos1 = e.toString().indexOf("(sum): ");
                    String sales = e.toString().substring(pos1 + 7);
                    for (int i = 0; i < much.length; i++) {
                        if (much[i] == Float.parseFloat(sales)) {
                            pos1 = i;
                            break;
                        }
                    }
                    String employee = chs[pos1];
                    Toast.makeText(getActivity(), "幣值:" + employee + "\n" + "共投資:" + sales + "新臺幣", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected() {
                }
            });
            addDataSet();
        } else if (cursor2.getCount() == 0) {
            pieChart.setCenterText("請新增資料");
        }
        return rootView;
    }

    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        for (int i = 0; i < chs.length; i++) {
            if (null == chs[i]) {
                list.remove(chs[i]);
            } else {
                yEntrys.add(new PieEntry(much[i], chs[i]));
            }
        }
        PieDataSet pieDataSet = new PieDataSet(yEntrys, null);

        //資料與資料間的距離
        pieDataSet.setSliceSpace(0);
        //圓餅圖上的文本大小
        pieDataSet.setValueTextSize(12);
        //圓餅圖上的文本顏色
        pieDataSet.setValueTextColor(Color.WHITE);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(113, 147, 123));
        colors.add(Color.rgb(194, 177, 19));
        colors.add(Color.rgb(66, 64, 69));
        colors.add(Color.rgb(207, 163, 152));
        colors.add(Color.rgb(66, 64, 73));
        colors.add(Color.rgb(116, 130, 144));
        colors.add(Color.rgb(165, 175, 187));
        colors.add(Color.rgb(201, 184, 195));
        colors.add(Color.rgb(220, 157, 172));
        pieDataSet.setColors(colors);
        //設定比例圖
        Legend legend = pieChart.getLegend();
        //設定比例圖的形狀;
        legend.setForm(Legend.LegendForm.CIRCLE);
        //最左邊顯示
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        PieData pieData = new PieData(pieDataSet);
        //顯示%數
        pieData.setDrawValues(true);
        //大小
        pieData.setValueTextSize(16f);
        //顯示%
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.animateXY(2000, 2000); //設定動畫
        pieChart.invalidate();
    }
}
