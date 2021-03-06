package e.wilso.project_chart.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalJsonAnalyzeUtil {

   static String TAG = "LocalJsonAnalyzeUtil";

   public static String getJson(Context context, String fileName) {
      StringBuilder stringBuilder = new StringBuilder();
      //獲得assets資源管理器
      AssetManager assetManager = context.getAssets();
      //使用IO流讀取json檔內容
      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName), "utf-8"));
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
         }
      } catch(IOException e) {
         e.printStackTrace();
      }
      //Log.e(TAG, stringBuilder.toString());
      return stringBuilder.toString();
   }

   public static <T> T JsonToObject(String json, Class<T> type) {
      Gson gson = new Gson();
      return gson.fromJson(json, type);
   }

   public static <T> T JsonToObject(Context context, String fileName, Class<T> type) {
      Gson gson = new Gson();
      return gson.fromJson(getJson(context, fileName), type);
   }
}
