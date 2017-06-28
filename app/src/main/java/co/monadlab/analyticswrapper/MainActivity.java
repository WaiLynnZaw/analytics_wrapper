package co.monadlab.analyticswrapper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    HashMap<String, String> data = new HashMap<>();
    data.put("Foo","Bar");
    MyApp.getWrapper().screenView("Main", data);
    MyApp.getWrapper().event("Main", "Test", "Testing Wrapper", data);
  }
}
