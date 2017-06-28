package co.monadlab.analyticswrapper;

import android.app.Application;
import android.content.Context;

import java.util.UUID;

import co.monadlab.analyticswrapper.library.AnalyticsWrapper;

/**
 * Created by WaiLynnZaw on 6/28/17.
 */

public class MyApp extends Application {
  private static final String GA_PROPERTY_ID = "UA-XXXXXX-X";
  private static MyApp instance;
  private static AnalyticsWrapper wrapper;
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    wrapper = new AnalyticsWrapper.Builder(getContext())
            .trackerId(GA_PROPERTY_ID)
            .quietMode(false)
            .uuid(UUID.randomUUID().toString()) // Should be saved with preferences for UUID
            .build();
  }
  private static Context getContext(){
    return instance;
  }
  public static AnalyticsWrapper getWrapper(){
    return wrapper;
  }
}