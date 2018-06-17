package e.wilso.project_chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;

public class PieChartActivity extends AppCompatActivity {

   PieChart pieChart;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_pie_chart);


   }
}
