package co.monadlab.analyticswrapper.library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WaiLynnZaw on 6/28/17.
 */

public class AnalyticsWrapper {
  private String trackerId;
  private final String TAG = getClass().getName();
  private Context context;

  public AnalyticsWrapper(Context context, String trackerId){
    this.context = context;
    this.trackerId = trackerId;
  }

  private HashMap<String, String> combineHashmap(HashMap<String, String> first, HashMap<String,String> second){
    first.putAll(second);
    return first;
  }

  public void screenView(String name, HashMap<String,String> parameters){
    HashMap<String, String> data = new HashMap<>();
    data.put("cd", name);
    data.putAll(parameters);
    send("screenView", data);
  }

  public void event(String category, String action, String label, HashMap<String,String> parameters) {
    HashMap<String, String> data = new HashMap<>();
    data.put("ec", category);
    data.put("ea", action);
    data.put("el", label);
    data.putAll(parameters);
    send("event", parameters);
  }

  private void send(String type, HashMap<String,String> parameters) {
    if (trackerId == null){
      Log.e(TAG, "You must set your tracker ID");
    }
    HashMap<String, String> queryArguments = new HashMap<>();
    queryArguments.put("tid", trackerId);
    queryArguments.put("aid", "co.monadlab.analyticswrapper"); // app Identifier
    queryArguments.put("cid", UUID.randomUUID().toString()); // Unique User Identifier
    queryArguments.put("an", "Test"); // App Name
    queryArguments.put("av", getFormattedVersion()); // Formatted Version
    queryArguments.put("ua", getUserAgent()); // User Agent
    queryArguments.put("ul", getUserLocale()); // User Language
    queryArguments.put("sr", "320x240"); // Screen Resolution
    queryArguments.put("v", "1");
    queryArguments.put("t", type);
    HashMap<String, String> arguments = combineHashmap(queryArguments, parameters);
    RestService.Implementation.get().track(arguments).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.e("Res", call.request().url().toString());
        Log.e("Res", response.body().toString());
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Err", t.getLocalizedMessage());
      }
    });
  }

  private String getUserLocale(){
    return Locale.getDefault().getDisplayLanguage();
  }
  private String getFormattedVersion(){
    PackageInfo pi;
    try {
      pi = context.getPackageManager()
              .getPackageInfo(context.getPackageName(), 0);
      return String.format(Locale.ENGLISH,"%s %d", pi.versionName, pi.versionCode);
    } catch (PackageManager.NameNotFoundException e) {
      return null;
    }
  }

  private String getUserAgent(){
    PackageInfo pi;
    try {
      pi = context.getPackageManager()
              .getPackageInfo(context.getPackageName(), 0);
      return String.format("%s-%s-%s-%s", context.getPackageName(),"Android",pi.versionName,pi.versionCode);
    } catch (PackageManager.NameNotFoundException e) {
      return null;
    }
  }
}
