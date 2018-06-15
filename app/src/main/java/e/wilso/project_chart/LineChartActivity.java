package e.wilso.project_chart;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import e.wilso.project_chart.modules.DateUtil;
import e.wilso.project_chart.modules.IncomeBean;
import e.wilso.project_chart.modules.LineChartBean;
import e.wilso.project_chart.modules.LocalJsonAnalyzeUtil;

public class LineChartActivity extends AppCompatActivity {

   private LineChart lineChart;
   private XAxis xAxis;                //X軸
   private YAxis leftYAxis;            //左側Y軸
   private YAxis rightYaxis;           //右側Y軸
   private Legend legend;              //圖例
   private LimitLine limitLine;        //限制線
   //private MyMarkerView markerView;  //標記視圖 即點擊xy軸交點時彈出展示資訊的View 需自訂

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_line_chart);

      lineChart = findViewById(R.id.lineChart);
      iniChart(lineChart);

      LineChartBean lineChartBean = LocalJsonAnalyzeUtil.JsonToObject(this, "chart.json", LineChartBean.class);
      List<IncomeBean> list = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();
      //showLineChart(list, "我的收益", Color.CYAN);
      showLineChart(list, "我的收益", getResources().getColor(R.color.blue));
      /*Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
      setChartFillDrawable(drawable);*/
   }

   private void iniChart(LineChart lineChart) {
      /***圖表設置***/
      //是否展示格線
      lineChart.setDrawGridBackground(false);
      lineChart.setBackgroundColor(Color.WHITE);
      //是否顯示邊界
      lineChart.setDrawBorders(false);
      //是否可以拖動
      //lineChart.setDragEnabled(false);
      lineChart.setDoubleTapToZoomEnabled(false);
      //是否有觸摸事件
      lineChart.setTouchEnabled(true);
      //設置XY軸動畫效果
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

      //禁止網格線
      xAxis.setDrawGridLines(false);
      rightYaxis.setDrawGridLines(false);
      leftYAxis.setDrawGridLines(false);

      //設置X Y軸網格線為虛線
      leftYAxis.enableGridDashedLine(10f, 10f, 0f);
      rightYaxis.setEnabled(false);
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

      //不顯示點
      lineDataSet.setDrawCircles(false);

      if (mode == null) {
         //設置曲線展示為圓滑曲線（如果不設置則默認折線）
         lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
      } else {
         lineDataSet.setMode(mode);
      }

      lineDataSet.setValueFormatter(new IValueFormatter() {
         @Override
         public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            DecimalFormat df = new DecimalFormat(".00");

            return df.format(value * 100) + "%";
         }
      });

      //不顯示值
      lineDataSet.setDrawValues(false);
   }

   /**
    * 展示曲線
    *
    * @param dataList 資料集合
    * @param name     曲線名稱
    * @param color    曲線顏色
    */
   private void showLineChart(final List<IncomeBean> dataList, String name, int color) {
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

      xAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            String tradeDate = dataList.get((int)value%dataList.size()).getTradeDate();
            return DateUtil.formatDate(tradeDate);
         }
      });

      //設置X軸分割數量
      xAxis.setLabelCount(6, false);

      //將Y軸分為 8份
      leftYAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            return ((int)(value * 100)) + "%";
         }
      });
      leftYAxis.setLabelCount(8, true);
   }

   /**
    * 設置線條填充背景顏色
    *
    * @param drawable
    */
   public void setChartFillDrawable(Drawable drawable) {
      if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
         LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
         //避免在 initLineDataSet()方法中 設置了 lineDataSet.setDrawFilled(false); 而無法實現效果
         lineDataSet.setDrawFilled(true);
         lineDataSet.setFillDrawable(drawable);
         lineChart.invalidate();
      }
   }
}

