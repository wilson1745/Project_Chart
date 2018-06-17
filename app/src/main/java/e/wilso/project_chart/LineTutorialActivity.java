package e.wilso.project_chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineTutorialActivity extends AppCompatActivity {

   private static final String TAG = "LineTutorialActivity";

   private LineChart mChart;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_line_tutorial);

      mChart = findViewById(R.id.linechart);

      //mChart.setOnChartGestureListener(this);
      //mChart.setOnChartValueSelectedListener(this);

      mChart.setDragEnabled(true);
      mChart.setScaleEnabled(false);

      //set data
      ArrayList<Entry> yValues = new ArrayList<>();

      yValues.add(new Entry(0, 60f));
      yValues.add(new Entry(1, 50f));
      yValues.add(new Entry(2, 70f));
      yValues.add(new Entry(3, 30f));
      yValues.add(new Entry(4, 50f));
      yValues.add(new Entry(5, 60f));
      yValues.add(new Entry(6, 65f));

      //put into line data database
      LineDataSet set1 = new LineDataSet(yValues, "Data set 1");

      set1.setFillAlpha(110);

      //set text, color
      set1.setColor(Color.RED);
      set1.setLineWidth(3f);
      set1.setValueTextSize(10f);
      set1.setValueTextColor(Color.GREEN);

      ArrayList<ILineDataSet> datasets = new ArrayList<>();
      datasets.add(set1);

      LineData data = new LineData(datasets);

      mChart.setData(data);
   }
}
