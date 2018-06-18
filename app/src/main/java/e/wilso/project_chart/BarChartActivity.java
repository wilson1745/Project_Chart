package e.wilso.project_chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

import e.wilso.project_chart.manager.BarChartManager;

public class BarChartActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_bar_chart);

      BarChart barChart1 = findViewById(R.id.bar_chart1);
      BarChart barChart2 = findViewById(R.id.bar_chart2);

      BarChartManager barChartManager1 = new BarChartManager(barChart1);
      BarChartManager barChartManager2 = new BarChartManager(barChart2);

      //設置x軸的數據
      ArrayList<Float> xValues = new ArrayList<>();
      for (int i = 0; i <= 10; i++) {
         xValues.add((float) i);
      }

      //設置y軸的數據()
      List<List<Float>> yValues = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
         List<Float> yValue = new ArrayList<>();
         for (int j = 0; j <= 10; j++) {
            yValue.add((float) (Math.random() * 80));
         }
         yValues.add(yValue);
      }

      //顏色集合
      List<Integer> colours = new ArrayList<>();
      colours.add(Color.GREEN);
      colours.add(Color.BLUE);
      colours.add(Color.RED);
      colours.add(Color.CYAN);

      //線的名字集合
      List<String> names = new ArrayList<>();
      names.add("折線一");
      names.add("折線二");
      names.add("折線三");
      names.add("折線四");

      //創建多條折線的圖表
      barChartManager1.showBarChart(xValues, yValues.get(0), names.get(1), colours.get(3));
      barChartManager2.showBarChart(xValues, yValues, names, colours);
      barChartManager2.setXAxis(11f, 0f, 11);
   }
}