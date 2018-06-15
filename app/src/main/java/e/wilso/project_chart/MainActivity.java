package e.wilso.project_chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      LineChart mlineChart = findViewById(R.id.lineChart);
      //顯示邊界
      mlineChart.setDrawBorders(true);
      //設置數據
      List<Entry> entries = new ArrayList<>();
      for(int i=0; i<10; i++) {
         entries.add(new Entry(i, (float) (Math.random()) * 80));
      }
      //一個LineDataSet就是一條線
      LineDataSet lineDataSet = new LineDataSet(entries, "溫度");
      LineData data = new LineData(lineDataSet);
      mlineChart.setData(data);

   }
}
