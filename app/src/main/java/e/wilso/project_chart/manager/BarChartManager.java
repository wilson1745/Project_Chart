package e.wilso.project_chart.manager;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class BarChartManager {

   private BarChart mBarChart;
   private YAxis leftAxis;
   private YAxis rightAxis;
   private XAxis xAxis;

   public BarChartManager(BarChart mBarChart) {
      this.mBarChart = mBarChart;

      leftAxis = mBarChart.getAxisLeft();
      rightAxis = mBarChart.getAxisRight();
      xAxis = mBarChart.getXAxis();
   }

   /**
    * 初始化LineChart
    */
   private void initLineChart() {
      //背景顏色
      mBarChart.setBackgroundColor(Color.WHITE);
      //網格
      mBarChart.setDrawGridBackground(false);
      //背景陰影
      mBarChart.setDrawBarShadow(false);
      mBarChart.setHighlightFullBarEnabled(false);

      //顯示邊界
      mBarChart.setDrawBorders(true);
      //設置動畫效果
      mBarChart.animateY(1000, Easing.EasingOption.Linear);
      mBarChart.animateX(1000, Easing.EasingOption.Linear);

      //折線圖例 標籤 設置
      Legend legend = mBarChart.getLegend();
      legend.setForm(Legend.LegendForm.LINE);
      legend.setTextSize(11f);
      //顯示位置
      legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
      legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
      legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
      legend.setDrawInside(false);

      //XY軸的設置
      //X軸設置顯示位置在底部
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
      xAxis.setGranularity(1f);
      //保證Y軸從0開始，不然會上移一點
      leftAxis.setAxisMinimum(0f);
      rightAxis.setAxisMinimum(0f);
   }

   /**
    * 展示柱狀圖(一條)
    *  @param xAxisValues
    * @param yAxisValues
    * @param label
    * @param color
    */
   public void showBarChart(ArrayList<Float> xAxisValues, List<Float> yAxisValues, String label, int color) {
      initLineChart();
      ArrayList<BarEntry> entries = new ArrayList<>();
      for (int i = 0; i < xAxisValues.size(); i++) {
         entries.add(new BarEntry(xAxisValues.get(i), yAxisValues.get(i)));
      }
      // 每一個BarDataSet代表一類柱狀圖
      BarDataSet barDataSet = new BarDataSet(entries, label);

      barDataSet.setColor(color);
      barDataSet.setValueTextSize(9f);
      barDataSet.setFormLineWidth(1f);
      barDataSet.setFormSize(15.f);

      ArrayList<IBarDataSet> dataSets = new ArrayList<>();
      dataSets.add(barDataSet);
      BarData data = new BarData(dataSets);
      //設置X軸的刻度數
      xAxis.setLabelCount(xAxisValues.size() - 1, false);
      mBarChart.setData(data);
   }

   /**
    * 展示柱狀圖(多條)
    *
    * @param xAxisValues
    * @param yAxisValues
    * @param labels
    * @param colours
    */
   public void showBarChart(List<Float> xAxisValues, List<List<Float>> yAxisValues, List<String> labels, List<Integer> colours) {
      initLineChart();
      BarData data = new BarData();

      for (int i = 0; i < yAxisValues.size(); i++) {
         ArrayList<BarEntry> entries = new ArrayList<>();
         for (int j = 0; j < yAxisValues.get(i).size(); j++) {
            entries.add(new BarEntry(xAxisValues.get(j), yAxisValues.get(i).get(j)));
         }
         BarDataSet barDataSet = new BarDataSet(entries, labels.get(i));

         barDataSet.setColor(colours.get(i));
         barDataSet.setValueTextColor(colours.get(i));
         barDataSet.setValueTextSize(10f);
         barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
         data.addDataSet(barDataSet);
      }
      int amount = yAxisValues.size();

      float groupSpace = 0.12f; //柱狀圖組之間的間距
      float barSpace = (float) ((1 - 0.12) / amount / 10); // x4 DataSet
      float barWidth = (float) ((1 - 0.12) / amount / 10 * 9); // x4 DataSet

      // (0.2 + 0.02) * 4 + 0.08 = 1.00 -> interval per "group"
      xAxis.setLabelCount(xAxisValues.size() - 1, false);

      //柱狀圖寬度
      data.setBarWidth(barWidth);

      //(起始點、柱狀圖組間距、柱狀圖之間間距)
      data.groupBars(0, groupSpace, barSpace);
      mBarChart.setData(data);
   }


   /**
    * 設置Y軸值
    *
    * @param max
    * @param min
    * @param labelCount
    */
   public void setYAxis(float max, float min, int labelCount) {
      if (max < min) {
         return;
      }
      leftAxis.setAxisMaximum(max);
      leftAxis.setAxisMinimum(min);
      leftAxis.setLabelCount(labelCount, false);

      rightAxis.setAxisMaximum(max);
      rightAxis.setAxisMinimum(min);
      rightAxis.setLabelCount(labelCount, false);
      mBarChart.invalidate();
   }

   /**
    * 設置X軸的值
    *
    * @param max
    * @param min
    * @param labelCount
    */
   public void setXAxis(float max, float min, int labelCount) {
      xAxis.setAxisMaximum(max);
      xAxis.setAxisMinimum(min);
      xAxis.setLabelCount(labelCount, false);

      mBarChart.invalidate();
   }

   /**
    * 設置高限制線
    *
    * @param high
    * @param name
    */
   public void setHightLimitLine(float high, String name, int color) {
      if (name == null) {
         name = "高限制線";
      }
      LimitLine hightLimit = new LimitLine(high, name);
      hightLimit.setLineWidth(4f);
      hightLimit.setTextSize(10f);
      hightLimit.setLineColor(color);
      hightLimit.setTextColor(color);
      leftAxis.addLimitLine(hightLimit);
      mBarChart.invalidate();
   }

   /**
    * 設置低限制線
    *
    * @param low
    * @param name
    */
   public void setLowLimitLine(int low, String name) {
      if (name == null) {
         name = "低限制線";
      }
      LimitLine hightLimit = new LimitLine(low, name);
      hightLimit.setLineWidth(4f);
      hightLimit.setTextSize(10f);
      leftAxis.addLimitLine(hightLimit);
      mBarChart.invalidate();
   }

   /**
    * 設置描述資訊
    *
    * @param str
    */
   public void setDescription(String str) {
      Description description = new Description();
      description.setText(str);
      mBarChart.setDescription(description);
      mBarChart.invalidate();
   }
}

