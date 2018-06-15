package e.wilso.project_chart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class IntroductionActivity extends AppCompatActivity {

   final String TAG = "Introduction";
   protected String[] mMonths = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};

   LineChart mlineChart;
   List<Entry> entries;
   LineDataSet lineDataSet;
   LineData data;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_introduction);

      mlineChart = findViewById(R.id.lineChart);

      //顯示邊界
      mlineChart.setDrawBorders(true);

      //設置數據
      entries = new ArrayList<>();
      for(int i=0; i<12; i++) {
         entries.add(new Entry(i, (float) (Math.random()) * 100));
      }

      //一個LineDataSet就是一條線
      lineDataSet = new LineDataSet(entries, "分數");
      data = new LineData(lineDataSet);
      mlineChart.setData(data);

      //XAxis(X軸)
      XAxisManager();

      //YAxis(Y軸)
      //YAxisManager();

      //Legend(曲線圖下面的 分數)
      //LegendManager();
   }

   private void XAxisManager() {
      //得到X軸
      XAxis xAxis = mlineChart.getXAxis();
      //設置X軸的位置
      xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
      //設置X軸坐標之間的最小間隔
      xAxis.setGranularity(1f);
      //設置X軸的刻度數量
      xAxis.setLabelCount(12, true);
      //設置X軸的最小值、最大值
      xAxis.setAxisMinimum(0f);
      xAxis.setAxisMaximum(11f);
      //設置X軸值為字符串
      xAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            return mMonths[(int) value % mMonths.length];
         }
      });
      //取消曲線顯示的值為整數
      lineDataSet.setValueFormatter(new IValueFormatter() {
         @Override
         public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            int IValue = (int) value;
            return String.valueOf(IValue);
         }
      });
   }

   private void YAxisManager() {
      //得到Y軸
      YAxis leftYAxis = mlineChart.getAxisLeft();
      YAxis rightYAxis = mlineChart.getAxisRight();
      //設置從Y軸值
      leftYAxis.setAxisMinimum(0f);
      leftYAxis.setAxisMaximum(100f);
      rightYAxis.setAxisMinimum(0f);
      rightYAxis.setAxisMaximum(100f);
      leftYAxis.setValueFormatter(new IAxisValueFormatter() {
         @Override
         public String getFormattedValue(float value, AxisBase axis) {
            return (int) value + "%";
         }
      });
      //設置Y軸是否顯示
      rightYAxis.setEnabled(false);
      //X軸和Y軸類似，都具有相同的內容方法
      leftYAxis.setGranularity(1f);
      leftYAxis.setLabelCount(11, false);
      leftYAxis.setTextColor(Color.BLUE); //文字顏色
      leftYAxis.setGridColor(Color.RED); //網格線顏色
      leftYAxis.setAxisLineColor(Color.GREEN); //Y軸顏色
      //限制線LimitLine
      LimitLine limitLine = new LimitLine(95, "高限制性"); //得到限制線
      limitLine.setLineWidth(4f);//寬度
      limitLine.setTextSize(10f);
      limitLine.setTextColor(Color.RED);  //顏色
      limitLine.setLineColor(Color.BLUE);
      leftYAxis.addLimitLine(limitLine);//Y軸添加限制線
   }

   private void LegendManager() {
      //得到Legend
      Legend legend = mlineChart.getLegend();
      //設置Lengend位置
      legend.setTextColor(Color.CYAN);//設置Legend 文本顏色
      legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
      legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
      legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
      //設置標簽是否換行
      legend.setWordWrapEnabled(true);
      //隱藏Lengend
      legend.setEnabled(false);
   }

}
