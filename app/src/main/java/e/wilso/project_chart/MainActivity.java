package e.wilso.project_chart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import e.wilso.project_chart.tutorial.BarTutorialActivity;
import e.wilso.project_chart.tutorial.LineTutorialActivity;
import e.wilso.project_chart.tutorial.PieTutorialActivity;

public class MainActivity extends AppCompatActivity {

   final String TAG = "MainActivity";
   final String[] item = {"Introduction", "LineChart Tutorial", "LineChart", "BarChart Tutorial", "BarChart", "PieChart Tutorial", "PieChar"};
   Intent intent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final ListView listView = findViewById(R.id.listview);
      final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
      listView.setAdapter(adapter);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            action(position);
         }
      });
   }

   private void action(int position) {
      switch (position) {
         case 0:
            intent = new Intent(this, IntroductionActivity.class);
            startActivity(intent);
            break;
         case 1:
            intent = new Intent(this, LineTutorialActivity.class);
            startActivity(intent);
            break;
         case 2:
            intent = new Intent(this, LineChartActivity.class);
            startActivity(intent);
            break;
         case 3:
            intent = new Intent(this, BarTutorialActivity.class);
            startActivity(intent);
            break;
         case 4:
            //intent = new Intent(this, BarChartActivity.class);
            //startActivity(intent);
            break;
         case 5:
            intent = new Intent(this, PieTutorialActivity.class);
            startActivity(intent);
            break;
         case 6:
            intent = new Intent(this, PieChartActivity.class);
            startActivity(intent);
            break;
         default:
            break;
      }
   }
}
