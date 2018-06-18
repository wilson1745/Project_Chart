package e.wilso.project_chart.utils;

import java.text.ParseException;
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

   public static String formatDateToYMD(String str) {
      SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
      String formatStr = "";
      try {
         formatStr = sf2.format(sf1.parse(str));
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return formatStr;
   }
}
