package e.wilso.project_chart.modules;

import java.text.SimpleDateFormat;

public class DateUtil {

   public static String formatDate(String str) {
      SimpleDateFormat s1 = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat s2 = new SimpleDateFormat("MM-dd");
      String formatStr = "";

      try {
         formatStr = s2.format(s1.parse(str));
      }catch (Exception e) {
         e.printStackTrace();
      }

      return formatStr;
   }
}
