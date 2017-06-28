package co.monadlab.analyticswrapper.library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by WaiLynnZaw on 6/28/17.
 */

public class AnalyticsWrapper {
  private String trackerId;
  private final String TAG = "co.monadlab.gawrapper";
  private Context context;
  private  boolean quietMode = true;
  private  String uuid = "";

  private AnalyticsWrapper(Builder builder){
    this.context = builder.context;
    this.trackerId = builder.trackerId;
    this.quietMode = builder.quietMode;
    this.uuid = builder.uuid;
  }

  public void screenView(String name){
    HashMap<String, Object> data = new HashMap<>();
    data.put("cd", name);
    send("screenview", data);
    if (!quietMode){
      Log.i(TAG, "Screen View Recorded for "+ name);
    }
  }

  public void event(String category, String action, String label, int value) {
    HashMap<String, Object> data = new HashMap<>();
    data.put("ec", category);
    data.put("ea", action);
    data.put("el", label);
    data.put("ev", value);
    send("event", data);
    if (!quietMode){
      Log.i(TAG, "Event Recorded for "+ category);
    }
  }

  private void send(String type, HashMap<String,Object> parameters) {
    if (trackerId == null){
      Log.e(TAG, "You must set your tracker ID");
    }
    HashMap<String, Object> queryArguments = new HashMap<>();

    PackageManager pm;
    PackageInfo pi;

    try {
      pm = context.getPackageManager();
      pi = pm.getPackageInfo(context.getPackageName(), 0);
      String appId = context.getPackageName();
      String appName =
              (String)pm.getApplicationLabel(pm.getApplicationInfo(appId, PackageManager.GET_META_DATA));
      queryArguments.put("tid", trackerId);
      queryArguments.put("aid", appId); // app Identifier
      queryArguments.put("cid", uuid); // Unique User Identifier
      queryArguments.put("an", appName); // App Name
      queryArguments.put("av", pi.versionName); // Formatted Version
      queryArguments.put("ul", getUserLocale()); // User Language
      queryArguments.put("sr", getResolution()); // Screen Resolution
      queryArguments.put("ds", "app");
      queryArguments.put("v", "1");
      queryArguments.put("t", type);

    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    HashMap<String, Object> arguments = combineHashMap(queryArguments, parameters);

    if (!quietMode){
      Log.i(TAG, "Sending information to google analytics...");
    }

    RestService.Implementation.get().track(arguments).enqueue(RestService.emptyCallback);
  }

  private HashMap<String, Object> combineHashMap(HashMap<String, Object> first, HashMap<String,Object> second){
    first.putAll(second);
    return first;
  }

  private String getUserLocale(){
    return Locale.getDefault().getDisplayLanguage();
  }

  private String getResolution(){
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;
    int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    return String.format(Locale.ENGLISH,"%dx%d", width, height);
  }

  interface RestService {
    @POST("collect")
    Call<ResponseBody> track(@QueryMap HashMap<String, Object> data);

    class Implementation {
      static RestService get() {
        return new Retrofit.Builder()
                .baseUrl("https://www.google-analytics.com/")
                .build()
                .create(RestService.class);
      }
    }

    Callback<ResponseBody> emptyCallback = new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Client", t.getLocalizedMessage());
      }
    };
  }
  public static class Builder{
    private String trackerId;
    private String uuid;
    private boolean quietMode;
    private Context context;

    public Builder(Context context){
      this.context = context;
    }

    public Builder trackerId(String trackerId){
      this.trackerId = trackerId;
      return this;
    }
    public Builder uuid(String uuid){
      this.uuid = uuid;
      return this;
    }
    public Builder quietMode(boolean quietMode){
      this.quietMode = quietMode;
      return this;
    }
    public AnalyticsWrapper build() {
      return new AnalyticsWrapper(this);
    }
  }
}
