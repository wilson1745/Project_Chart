package e.wilso.project_chart.tutorial;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import e.wilso.project_chart.R;

public class LineTutorialActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

   private static final String TAG = "LineTutorialActivity";

   private LineChart mChart;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_line_tutorial);

      mChart = findViewById(R.id.linechart);

      mChart.setOnChartGestureListener(this);
      mChart.setOnChartValueSelectedListener(this);

      mChart.setDragEnabled(true);
      mChart.setScaleEnabled(false);

      //limit line
      LimitLine upper_line = new LimitLine(65f, "Danger");
      upper_line.setLineWidth(4f);
      upper_line.enableDashedLine(10f, 10, 0f);
      upper_line.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
      upper_line.setTextSize(15f);

      //limit line
      LimitLine lower_line = new LimitLine(35f, "Too Low");
      lower_line.setLineWidth(4f);
      lower_line.enableDashedLine(10f, 10, 0f);
      lower_line.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
      lower_line.setTextSize(15f);

      YAxis leftAxis = mChart.getAxisLeft();
      leftAxis.removeAllLimitLines();
      leftAxis.addLimitLine(upper_line);
      leftAxis.addLimitLine(lower_line);
      leftAxis.setAxisMaximum(100f);
      leftAxis.setAxisMinimum(0f);
      leftAxis.enableGridDashedLine(10f, 10f, 0);
      leftAxis.setDrawLimitLinesBehindData(true);

      mChart.getAxisRight().setEnabled(false);

      //set data
      ArrayList<Entry> yValues = new ArrayList<>();

      for(int i=0; i<7; i++) {
         float ran = (float) (Math.random()*100);
         yValues.add(new Entry(i, ran));
      }

      /*yValues.add(new Entry(0, 60f));
      yValues.add(new Entry(1, 50f));
      yValues.add(new Entry(2, 70f));
      yValues.add(new Entry(3, 30f));
      yValues.add(new Entry(4, 50f));
      yValues.add(new Entry(5, 60f));
      yValues.add(new Entry(6, 65f));*/

      //put into line data database
      LineDataSet set1 = new LineDataSet(yValues, "Data set 1");

      set1.setFillAlpha(110);

      //set text, color
      set1.setColor(Color.RED);
      set1.setLineWidth(3f);
      set1.setValueTextSize(10f);
      set1.setValueTextColor(Color.BLUE);

      ArrayList<ILineDataSet> datasets = new ArrayList<>();
      datasets.add(set1);

      LineData data = new LineData(datasets);

      mChart.setData(data);

      String[] values = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"};

      XAxis xAxis = mChart.getXAxis();
      xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
      xAxis.setGranularity(1);
      xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
   }

   @Override
   public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
      Log.e(TAG, "onChartGestureStart: X: " + me.getX() + " Y: " + me.getY());
   }

   @Override
   public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
      Log.e(TAG, "onChartGestureEnd: " + lastPerformedGesture);
   }

   @Override
   public void onChartLongPressed(MotionEvent me) {
      Log.e(TAG, "onChartLongPressed: ");
   }

   @Override
   public void onChartDoubleTapped(MotionEvent me) {
      Log.e(TAG, "onChartDoubleTapped: ");
   }

   @Override
   public void onChartSingleTapped(MotionEvent me) {
      Log.e(TAG, "onChartSingleTapped: ");
   }

   @Override
   public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
      Log.e(TAG, "onChartFling: veloX: " + velocityX + " veloY: " + velocityY);
   }

   @Override
   public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
      Log.e(TAG, "onChartScale: scaleX: " + scaleX + " scaleY: " + scaleY);
   }

   @Override
   public void onChartTranslate(MotionEvent me, float dX, float dY) {
      Log.e(TAG, "onChartTranslate: dX: " + dX + " dY: " + dY);
   }

   @Override
   public void onValueSelected(Entry e, Highlight h) {
      Log.e(TAG, "onValueSelected: dX: " + e.toString());
   }

   @Override
   public void onNothingSelected() {
      Log.e(TAG, "onNothingSelected: ");
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
