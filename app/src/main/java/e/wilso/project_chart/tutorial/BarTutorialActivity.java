package e.wilso.project_chart.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import e.wilso.project_chart.R;

public class BarTutorialActivity extends AppCompatActivity {

   BarChart barChart;

   String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May"};

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_bar_tutorial);

      barChart = findViewById(R.id.barchart);

      barChart.setDrawBarShadow(false);
      barChart.setDrawValueAboveBar(true);
      barChart.setMaxVisibleValueCount(50);
      barChart.setPinchZoom(false);
      barChart.setDrawGridBackground(true);

      ArrayList<BarEntry> barEntries = new ArrayList<>();
      for(int i=0; i<months.length; i++) {
         float ran = (float) (Math.random()*100+1);
         barEntries.add(new BarEntry(i, ran));
      }

      ArrayList<BarEntry> barEntries1 = new ArrayList<>();
      for(int j=0; j<months.length; j++) {
         float ran = (float) (Math.random()*100+1);
         barEntries1.add(new BarEntry(j, ran));
      }

      BarDataSet barDataSet = new BarDataSet(barEntries, "Data Set 1");
      barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

      BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Data Set 2");
      barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

      BarData data = new BarData(barDataSet, barDataSet1);

      //space between two bars
      float groupSpace = 0.25f;
      float barSpace = 0.02f;
      float barWidth = 0.3f;

      barChart.setData(data);

      //data.setBarWidth(0.9f);
      data.setBarWidth(barWidth);
      barChart.groupBars(0, groupSpace, barSpace);

      XAxis xAxis = barChart.getXAxis();
      xAxis.setValueFormatter(new MyXAxisValueFormatter(months));
      xAxis.setLabelCount(months.length, false);

      xAxis.setGranularity(1);
      xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
      //xAxis.setCenterAxisLabels(true);
      //xAxis.setAxisMinimum(1);
   }

   public class MyXAxisValueFormatter implements IAxisValueFormatter {

      private String[] values;

      public MyXAxisValueFormatter(String[] values) {
         this.values = values;
      }

      @Override
      public String getFormattedValue(float value, AxisBase axis) {
         return values[(int) value];
      }
   }
}
