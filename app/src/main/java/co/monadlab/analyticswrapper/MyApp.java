package co.monadlab.analyticswrapper;

import android.app.Application;
import android.content.Context;

import co.monadlab.analyticswrapper.library.AnalyticsWrapper;

/**
 * Created by WaiLynnZaw on 6/28/17.
 */

public class MyApp extends Application {
  private static final String GA_PROPERTY_ID = "UA-86936848-3";

  static AnalyticsWrapper wrapper;

  private static MyApp instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    initialize();
  }

  private static Context getContext(){
    return instance;
  }


  public static synchronized void initialize() {
    wrapper = new AnalyticsWrapper(getContext(),GA_PROPERTY_ID);
  }

  public static AnalyticsWrapper getWrapper() {
    return wrapper;
  }
}