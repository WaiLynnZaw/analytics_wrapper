package co.monadlab.analyticswrapper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.monadlab.analyticswrapper.library.AnalyticsWrapper;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    AnalyticsWrapper.getWrapper().screenView("MainActivity");

    AnalyticsWrapper.getWrapper().event("Main", "Call", "Phone", 0);
  }
}
