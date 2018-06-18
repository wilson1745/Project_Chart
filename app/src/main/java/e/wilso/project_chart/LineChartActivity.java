package e.wilso.project_chart;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import e.wilso.project_chart.data.LineChartBean;
import e.wilso.project_chart.manager.LineChartManager;
import e.wilso.project_chart.modules.CompositeIndexBean;
import e.wilso.project_chart.modules.IncomeBean;
import e.wilso.project_chart.utils.LocalJsonAnalyzeUtil;

public class LineChartActivity extends AppCompatActivity {

   private LineChartBean lineChartBean;
   private List<IncomeBean> incomeBeanList;//個人收益
   private List<CompositeIndexBean> shanghai;//滬市指數
   private List<CompositeIndexBean> shenzheng;//深市指數
   private List<CompositeIndexBean> GEM;//創業板指數

   private LineChart lineChart1;

   private ConstraintLayout cl_shanghai;
   private View view_shanghai;
   private ConstraintLayout cl_shenzhen;
   private View view_shenzhen;
   private ConstraintLayout cl_gem;
   private View view_gem;

   private LineChartManager lineChartManager1;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_line_chart);

      initData();
      initView();

      //開場動畫
      lineChart1.animateY(1000, Easing.EasingOption.EaseInOutCirc);
   }

   private void initData() {
      //獲取資料
      lineChartBean = LocalJsonAnalyzeUtil.JsonToObject(this, "chart.json", LineChartBean.class);
      incomeBeanList = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();

      shanghai = lineChartBean.getGRID0().getResult().getCompositeIndexShanghai();
      shenzheng = lineChartBean.getGRID0().getResult().getCompositeIndexShenzhen();
      GEM = lineChartBean.getGRID0().getResult().getCompositeIndexGEM();
   }

   private void initView() {
      lineChart1 = findViewById(R.id.lineChart);
      lineChartManager1 = new LineChartManager(lineChart1);

      cl_shanghai = findViewById(R.id.cl_shanghai);
      view_shanghai = findViewById(R.id.view_shanghai);
      cl_shanghai.setOnClickListener(listener);

      cl_shenzhen = findViewById(R.id.cl_shenzhen);
      view_shenzhen = findViewById(R.id.view_shenzhen);
      cl_shenzhen.setOnClickListener(listener);

      cl_gem = findViewById(R.id.cl_gem);
      view_gem = findViewById(R.id.view_gem);
      cl_gem.setOnClickListener(listener);

      //展示圖表
      lineChartManager1.showLineChart(incomeBeanList, "我的收益", getResources().getColor(R.color.blue));
      lineChartManager1.addLine(shanghai, "上證指數", getResources().getColor(R.color.orange));

      //設置曲線填充色 以及 MarkerView
      Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
      lineChartManager1.setChartFillDrawable(drawable);
      lineChartManager1.setMarkerView(this);
   }

   View.OnClickListener listener = new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
      @Override
      public void onClick(View view) {
         switch (view.getId()) {
            case R.id.cl_shanghai:
               view_shanghai.setBackground(getResources().getDrawable(R.drawable.shape_round_orange));

               view_gem.setBackground(getResources().getDrawable(R.drawable.shape_round_gray));
               view_shenzhen.setBackground(getResources().getDrawable(R.drawable.shape_round_gray));

               lineChartManager1.resetLine(1, shanghai, "上證指數", getResources().getColor(R.color.orange));
               break;
            case R.id.cl_shenzhen:
               view_shenzhen.setBackground(getResources().getDrawable(R.drawable.shape_round_orange));

               view_gem.setBackground(getResources().getDrawable(R.drawable.shape_round_gray));
               view_shanghai.setBackground(getResources().getDrawable(R.drawable.shape_round_gray));

               lineChartManager1.resetLine(1, shenzheng, "深證指數", getResources().getColor(R.color.orange));
               break;
            case R.id.cl_gem:
               view_gem.setBackground(getResources().getDrawable(R.drawable.shape_round_orange));
               view_shanghai.setBackground(getResources().getDrawable(R.drawable.shape_round_gray));
               view_shenzhen.setBackground(getResources().getDrawable(R.drawable.shape_round_gray));

               lineChartManager1.resetLine(1, GEM, "創業指數", getResources().getColor(R.color.orange));
               break;
         }
      }
   };
}

