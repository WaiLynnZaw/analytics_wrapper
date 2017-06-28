package co.monadlab.analyticswrapper.library;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by WaiLynnZaw on 6/27/17.
 */

public interface RestService {
  @POST("collect")
  Call<ResponseBody> track(@QueryMap HashMap<String, String> data);

  class Implementation {
    public static RestService get() {
      return new Retrofit.Builder()
              .baseUrl("https://www.google-analytics.com/")
              .build()
              .create(RestService.class);
    }
  }
}
