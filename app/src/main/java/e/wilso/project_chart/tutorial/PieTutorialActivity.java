package e.wilso.project_chart.tutorial;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import e.wilso.project_chart.R;

public class PieTutorialActivity extends AppCompatActivity {

   PieChart pieChart;
   String[] nation = new String[] {"Bangladesh", "USA", "UK", "India", "Russia", "Japan"};

   PieChart pieChart1;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_pie_tutorial);

      firstPie();
      secondPie();
   }

   public void firstPie() {
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

      //set description
      Description description = new Description();
      description.setText("This is my first Pie Chart!!!");
      description.setTextSize(15);
      pieChart.setDescription(description);

      //開場動畫
      pieChart.animateY(1000, Easing.EasingOption.EaseInOutCirc);

      pieChart = setData(pieChart);

   }

   private void secondPie() {
      pieChart1 = findViewById(R.id.piechart1);
      pieChart1.setBackgroundColor(Color.BLUE);

      pieChart1.setUsePercentValues(true);
      pieChart1.getDescription().setEnabled(false);
      pieChart1.setExtraOffsets(5,10,5,5);

      pieChart1.setDragDecelerationFrictionCoef(0.99f);

      //圖片中間中空
      pieChart1.setDrawHoleEnabled(true);
      pieChart1.setHoleColor(Color.WHITE);
      //中空延伸區域
      pieChart1.setTransparentCircleRadius(61f);

      //set description
      Description description = new Description();
      description.setText("Pie Chart with half chart");
      description.setTextSize(15);
      pieChart1.setDescription(description);

      //開場動畫
      pieChart1.animateY(1000, Easing.EasingOption.EaseInOutCirc);

      //只有顯示半圓
      moveOffScreen();
      //角度、方位
      pieChart1.setMaxAngle(180);
      pieChart1.setRotationAngle(180);
      pieChart1.setCenterTextOffset(0, -20);

      pieChart1 = setData(pieChart1);
   }

   private PieChart setData(PieChart pie) {
      //儲存資料
      ArrayList<PieEntry> yValues = new ArrayList<>();
      for(int i=0;i<nation.length;i++) {
         float ran = (float) (Math.random()*100+1);
         yValues.add(new PieEntry(ran, nation[i].toString()));
      }

      /*yValues.add(new PieEntry(34f, "Bangladesh"));
      yValues.add(new PieEntry(23f, "USA"));
      yValues.add(new PieEntry(14f, "UK"));
      yValues.add(new PieEntry(35f, "India"));
      yValues.add(new PieEntry(40f, "Russia"));
      yValues.add(new PieEntry(23f, "Japan"));*/

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

      pie.setData(data);

      return pie;
   }

   private void moveOffScreen() {
      Display display = getWindowManager().getDefaultDisplay();
      DisplayMetrics metrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(metrics);
      int height = metrics.heightPixels;

      int offset = (int) (height*0.5);

      ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) pieChart1.getLayoutParams();
      params.setMargins(0,0,0,-offset);
      pieChart1.setLayoutParams(params);
   }
}
