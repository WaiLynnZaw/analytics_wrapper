package co.monadlab.analyticswrapper;

import android.app.Application;
import android.content.Context;

import co.monadlab.analyticswrapper.library.AnalyticsWrapper;

/**
 * Created by WaiLynnZaw on 6/28/17.
 */

public class MyApp extends Application {
  private static final String GA_PROPERTY_ID = "UA-101789214-1";
  private static MyApp instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    AnalyticsWrapper.initialize(getContext(),GA_PROPERTY_ID);
    AnalyticsWrapper.showLogs();
  }
  private static Context getContext(){
    return instance;
  }
}