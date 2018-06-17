package e.wilso.project_chart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieTutorialActivity extends AppCompatActivity {

   PieChart pieChart;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_pie_tutorial);

      pieChart = findViewById(R.id.piechart);

      pieChart.setUsePercentValues(true);
      pieChart.getDescription().setEnabled(false);
      pieChart.setExtraOffsets(5,10,5,5);

      pieChart.setDragDecelerationFrictionCoef(0.99f);

      //圖片中間中空
      pieChart.setDrawHoleEnabled(true);
      pieChart.setHoleColor(Color.WHITE);
      //中空延伸區域
      pieChart.setTransparentCircleRadius(61f);

      //儲存資料
      ArrayList<PieEntry> yValues = new ArrayList<>();
      yValues.add(new PieEntry(34f, "Bangladesh"));
      yValues.add(new PieEntry(23f, "USA"));
      yValues.add(new PieEntry(14f, "UK"));
      yValues.add(new PieEntry(35f, "India"));
      yValues.add(new PieEntry(40f, "Russia"));
      yValues.add(new PieEntry(23f, "Japan"));

      //set description
      Description description = new Description();
      description.setText("This is my first Pie Chart!!!");
      description.setTextSize(15);
      pieChart.setDescription(description);

      //開場動畫
      pieChart.animateY(1000, Easing.EasingOption.EaseInOutCirc);

      //set database
      PieDataSet dataSet = new PieDataSet(yValues, "Countries");

      //區塊間隔
      dataSet.setSliceSpace(3f);
      dataSet.setSelectionShift(5f);
      dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

      //set display
      PieData data = new PieData(dataSet);
      data.setValueTextSize(10f);
      data.setValueTextColor(Color.YELLOW);

      pieChart.setData(data);
   }
}
