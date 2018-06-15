package e.wilso.project_chart.modules;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartManager {


   private LineChart lineChart;
   private XAxis xAxis;
   private YAxis leftYAxis, rightYaxis;
   private Legend legend;
   private LimitLine limitLine;

   //private MyMarkerView markerView; //標記視圖即點擊xy軸交點時彈出展示資訊的查看需自定義


   public LineChartManager(LineChart lineChart) {
      this.lineChart = lineChart;

      leftYAxis = lineChart.getAxisLeft();
      rightYaxis = lineChart.getAxisRight();
      xAxis = lineChart.getXAxis();

      //初始化圖表
      initChart(lineChart);
   }

   private void initChart(LineChart lineChart) {
      /***图表设置***/
      //是否展示网格线
      lineChart.setDrawGridBackground(false);
      //是否显示边界
      lineChart.setDrawBorders(true);
      //是否可以拖动
      lineChart.setDragEnabled(false);
      //是否有触摸事件
      lineChart.setTouchEnabled(true);
      //设置XY轴动画效果
      lineChart.animateY(2500);
      lineChart.animateX(1500);

      /***XY軸的設置***/
      xAxis = lineChart.getXAxis();
      leftYAxis = lineChart.getAxisLeft();
      rightYaxis = lineChart.getAxisRight();
      //X軸設置顯示位置在底部
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
      xAxis.setAxisMinimum(0f);
      xAxis.setGranularity(1f);

      /***折線圖例 標籤 設置***/
      legend = lineChart.getLegend();
      //設置顯示類型，LINE CIRCLE SQUARE EMPTY 等等 多種方式，查看LegendForm 即可
      legend.setForm(Legend.LegendForm.LINE);
      legend.setTextSize(12f);
      //顯示位置 左下方
      legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
      legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
      legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
      //是否繪製在圖表裡面
      legend.setDrawInside(false);
   }

   /**
    * 曲線初始化設置 一個LineDataSet 代表一條曲線
    *
    * @param lineDataSet 線條
    * @param color       線條顏色
    * @param mode
    */
   private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
      lineDataSet.setColor(color);
      lineDataSet.setCircleColor(color);
      lineDataSet.setLineWidth(1f);
      lineDataSet.setCircleRadius(3f);
      //設置曲線值的圓點是實心還是空心
      lineDataSet.setDrawCircleHole(false);
      lineDataSet.setValueTextSize(10f);
      //設置折線圖填充
      lineDataSet.setDrawFilled(true);
      lineDataSet.setFormLineWidth(1f);
      lineDataSet.setFormSize(15.f);
      if (mode == null) {
         //設置曲線展示為圓滑曲線（如果不設置則默認折線）
         lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
      } else {
         lineDataSet.setMode(mode);
      }
   }

   /**
    * 展示曲線
    *
    * @param dataList 資料集合
    * @param name     曲線名稱
    * @param color    曲線顏色
    */
   public void showLineChart(List<IncomeBean> dataList, String name, int color) {
      List<Entry> entries = new ArrayList<>();
      for (int i=0; i<dataList.size(); i++) {
         IncomeBean data = dataList.get(i);
         /**
          * 在此可查看 Entry構造方法，可發現 可傳入數值 Entry(float x, float y)
          * 也可傳入Drawable， Entry(float x, float y, Drawable icon) 可在XY軸交點 設置Drawable圖像展示
          */
         Entry entry = new Entry(i, (float) data.getValue());
         entries.add(entry);
      }
      // 每一個LineDataSet代表一條線
      LineDataSet lineDataSet = new LineDataSet(entries, name);
      initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
      LineData lineData = new LineData(lineDataSet);
      lineChart.setData(lineData);
   }
}
