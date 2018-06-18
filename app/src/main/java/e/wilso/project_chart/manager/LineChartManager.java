package e.wilso.project_chart.manager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import e.wilso.project_chart.markerview.LineChartMarkView;
import e.wilso.project_chart.modules.CompositeIndexBean;
import e.wilso.project_chart.modules.IncomeBean;
import e.wilso.project_chart.utils.DateUtil;

public class LineChartManager {

   private LineChart lineChart;
   private XAxis xAxis;                //X軸
   private YAxis leftYAxis;            //左側Y軸
   private YAxis rightYAxis;           //右側Y軸 自訂XY軸值
   private Legend legend;              //圖例
   private LimitLine limitLine;        //限制線

   public LineChartManager(LineChart lineChart) {
      this.lineChart = lineChart;
      leftYAxis = lineChart.getAxisLeft();
      rightYAxis = lineChart.getAxisRight();
      xAxis = lineChart.getXAxis();

      initChart(lineChart);
   }

   /**
    * 初始化圖表
    */
   private void initChart(LineChart lineChart) {
      /***圖表設置***/
      //是否展示格線
      lineChart.setDrawGridBackground(false);
      lineChart.setBackgroundColor(Color.WHITE);
      //是否顯示邊界
      lineChart.setDrawBorders(false);
      //是否可以拖動
//        lineChart.setDragEnabled(false);
      lineChart.setDoubleTapToZoomEnabled(false);

      //是否有觸摸事件
//        lineChart.setTouchEnabled(true);

      //設置XY軸動畫效果
//        lineChart.animateY(500);
//        lineChart.animateX(500);
      Description description = new Description();
//        description.setText("需要展示的內容");
      description.setEnabled(false);
      lineChart.setDescription(description);


      /***XY軸的設置***/
      xAxis = lineChart.getXAxis();
      leftYAxis = lineChart.getAxisLeft();
      rightYAxis = lineChart.getAxisRight();

      xAxis.setDrawGridLines(false);
      rightYAxis.setDrawGridLines(false);
      leftYAxis.setDrawGridLines(true);
      //設置Y軸格線為虛線
      leftYAxis.enableGridDashedLine(10f, 10f, 0f);
      rightYAxis.setEnabled(false);

      //X軸設置顯示位置在底部
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
      xAxis.setAxisMinimum(0f);
      xAxis.setGranularity(1f);
      //保證Y軸從0開始，不然會上移一點
      leftYAxis.setAxisMinimum(0f);
      rightYAxis.setAxisMinimum(0f);

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
      //是否顯示
      legend.setEnabled(false);
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

      lineDataSet.setDrawCircles(false);
      lineDataSet.setDrawValues(false);
      //設置曲線值的圓點是實心還是空心
      lineDataSet.setDrawCircleHole(false);
      lineDataSet.setValueTextSize(10f);
      //設置折線圖填充
      lineDataSet.setDrawFilled(false);
      lineDataSet.setFormLineWidth(1f);
      lineDataSet.setFormSize(15.f);
      if (mode == null) {
         //設置曲線展示為圓滑曲線（如果不設置則默認折線）
         lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
      }
      else {
         lineDataSet.setMode(mode);
      }
   }

   /**
    * 展示一條曲線 預設x軸
    *
    * @param yData    y軸的數據
    * @param lineName 曲線名稱
    * @param color    曲線顏色
    */
   public void showOneLineChart(List<Float> yData, String lineName, int color) {
      ArrayList<Entry> entries = new ArrayList<>();
      for (int i = 0; i < yData.size(); i++) {
         entries.add(new Entry(yData.get(i), yData.get(i)));
      }

      // 每一個LineDataSet代表一條線
      LineDataSet lineDataSet = new LineDataSet(entries, lineName);
      // CUBIC_BEZIER 圓滑曲線
      initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);

      LineData data = new LineData();
      data.addDataSet(lineDataSet);
      lineChart.setData(data);
   }

   /**
    * 注意 集合的長度一致，在此未做處理
    *
    * @param yDataList List<Integer> 代表一條曲線的資料  yDataList.size 代表曲線的條數
    * @param lineNames 曲線名稱
    * @param colors    曲線顏色
    */
   public void showMultiNormalLineChart(List<List<Float>> yDataList, List<String> lineNames, List<Integer> colors) {
      ArrayList<ILineDataSet> dataSets = new ArrayList<>();
      for (int i = 0; i < yDataList.size(); i++) {
         ArrayList<Entry> entries = new ArrayList<>();

         for (int j = 0; j < yDataList.get(i).size(); j++) {
            entries.add(new Entry(yDataList.get(i).get(j), yDataList.get(i).get(j)));
         }
         LineDataSet lineDataSet = new LineDataSet(entries, lineNames.get(i));
         initLineDataSet(lineDataSet, colors.get(i), LineDataSet.Mode.CUBIC_BEZIER);
         dataSets.add(lineDataSet);
      }
      LineData lineData = new LineData(dataSets);
      lineChart.setData(lineData);
   }

   /**
    * 設置X軸的顯示值
    *
    * @param min        x軸最小值
    * @param max        x軸最大值
    * @param labelCount x軸的分割數量
    */
   public void setXAxisData(float min, float max, int labelCount) {
      xAxis.setAxisMinimum(min);
      xAxis.setAxisMaximum(max);
      xAxis.setLabelCount(labelCount, false);
      lineChart.invalidate();
   }

   /**
    * 自訂的 X軸顯示內容
    *
    * @param xAxisStr
    * @param labelCount x軸的分割數量
    */
   public void setXAxisData(final List<String> xAxisStr, int labelCount) {
      xAxis.setLabelCount(labelCount, false);
      xAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            return xAxisStr.get((int) value % xAxisStr.size());
         }
      });
      lineChart.invalidate();
   }

   /**
    * 設置Y軸值
    *
    * @param max
    * @param min
    * @param labelCount
    */
   public void setYAxisData(float max, float min, int labelCount) {
      leftYAxis.setAxisMaximum(max);
      leftYAxis.setAxisMinimum(min);
      leftYAxis.setLabelCount(labelCount, false);

      rightYAxis.setAxisMaximum(max);
      rightYAxis.setAxisMinimum(min);
      rightYAxis.setLabelCount(labelCount, false);
      lineChart.invalidate();
   }

   /**
    * 自訂的 y軸顯示內容
    *
    * @param yAxisStr
    * @param labelCount y軸的分割數量
    */
   public void setYAxisData(final List<String> yAxisStr, int labelCount) {
      xAxis.setLabelCount(labelCount, false);
      xAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            return yAxisStr.get((int) value % yAxisStr.size());
         }
      });
      lineChart.invalidate();
   }


   /**
    * 設置高限制線
    *
    * @param high
    * @param name
    */
   public void setHighLimitLine(float high, String name, int color) {
      if (name == null) {
         name = "高限制線";
      }
      LimitLine highLimit = new LimitLine(high, name);
      highLimit.setLineWidth(2f);
      highLimit.setTextSize(10f);
      highLimit.setLineColor(color);
      highLimit.setTextColor(color);
      leftYAxis.addLimitLine(highLimit);
      lineChart.invalidate();
   }

   /**
    * 設置低限制線
    *
    * @param low
    * @param name
    */
   public void setLowLimitLine(float low, String name, int color) {
      if (name == null) {
         name = "高限制線";
      }
      LimitLine lowLimit = new LimitLine(low, name);
      lowLimit.setLineWidth(2f);
      lowLimit.setTextSize(10f);
      lowLimit.setLineColor(color);
      lowLimit.setTextColor(color);
      leftYAxis.addLimitLine(lowLimit);
      lineChart.invalidate();
   }

   /**
    * 設置描述資訊
    *
    * @param str
    */
   public void setDescription(String str) {
      Description description = new Description();
      description.setText(str);
      lineChart.setDescription(description);
      lineChart.invalidate();
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


   /*****************以下方法無法通用，根據自己資料的不同進行相應的處理********************/
   /**
    * 展示曲線
    *
    * @param dataList 資料集合
    * @param name     曲線名稱
    * @param color    曲線顏色
    */
   public void showLineChart(final List<IncomeBean> dataList, String name, int color) {
      List<Entry> entries = new ArrayList<>();
      for (int i = 0; i < dataList.size(); i++) {
         IncomeBean data = dataList.get(i);
         /**
          * 在此可查看 Entry構造方法，可發現 可傳入數值 Entry(float x, float y)
          * 也可傳入Drawable， Entry(float x, float y, Drawable icon) 可在XY軸交點 設置Drawable圖像展示
          */
         Entry entry = new Entry(i, (float) data.getValue());
         entries.add(entry);
      }

      /******根據需求的不同 在此在次設置X Y軸的顯示內容******/
      xAxis.setLabelCount(6, false);
      //設置是否繪製刻度
      xAxis.setDrawGridLines(false);
      //是否繪製X軸線
      xAxis.setDrawAxisLine(false);

      xAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            String tradeDate = dataList.get((int) value % dataList.size()).getTradeDate();
            return DateUtil.formatDateToYMD(tradeDate);
         }
      });

      leftYAxis.setLabelCount(8);
      leftYAxis.setDrawGridLines(true);
      leftYAxis.setDrawZeroLine(true); // draw a zero line
      leftYAxis.setZeroLineColor(Color.GRAY);
      leftYAxis.setZeroLineWidth(1f);
      leftYAxis.setAxisLineWidth(1f);
      leftYAxis.setAxisLineColor(Color.GRAY);
      leftYAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            return ((int) (value * 100)) + "%";
         }
      });

      // 每一個LineDataSet代表一條線
      LineDataSet lineDataSet = new LineDataSet(entries, name);
      //LINEAR 折線圖  CUBIC_BEZIER 圓滑曲線
      initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
      //線條自訂內容 放在這裡
      lineDataSet.setValueFormatter(new IValueFormatter() {
         @Override
         public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            DecimalFormat df = new DecimalFormat(".00");
            return df.format(value * 100) + "%";
         }
      });

      LineData lineData = new LineData(lineDataSet);
      lineChart.setData(lineData);
   }

   /**
    * 添加曲線
    */
   public void addLine(List<CompositeIndexBean> dataList, String name, int color) {
      List<Entry> entries = new ArrayList<>();
      for (int i = 0; i < dataList.size(); i++) {
         CompositeIndexBean data = dataList.get(i);
         Entry entry = new Entry(i, (float) data.getRate());
         entries.add(entry);
      }
      // 每一個LineDataSet代表一條線
      LineDataSet lineDataSet = new LineDataSet(entries, name);
      initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
      lineChart.getLineData().addDataSet(lineDataSet);
      lineChart.invalidate();
   }

   /**
    * 重置某條曲線 position 從 0 開始
    */
   public void resetLine(int position, List<CompositeIndexBean> dataList, String name, int color) {
      LineData lineData = lineChart.getData();
      List<ILineDataSet> list = lineData.getDataSets();
      if (list.size() <= position) {
         return;
      }

      List<Entry> entries = new ArrayList<>();
      for (int i = 0; i < dataList.size(); i++) {
         CompositeIndexBean data = dataList.get(i);
         Entry entry = new Entry(i, (float) data.getRate());
         entries.add(entry);
      }

      LineDataSet lineDataSet = new LineDataSet(entries, name);
      initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);

      lineData.getDataSets().set(position, lineDataSet);
      lineChart.invalidate();
   }

   /**
    * 設置 可以顯示X Y 軸自訂值的 MarkerView
    */
   public void setMarkerView(Context context) {
      LineChartMarkView mv = new LineChartMarkView(context, xAxis.getValueFormatter());
      mv.setChartView(lineChart);
      lineChart.setMarker(mv);
      lineChart.invalidate();
   }
}
