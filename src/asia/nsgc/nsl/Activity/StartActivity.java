package asia.nsgc.nsl.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import asia.nsgc.nsl.R;
import asia.nsgc.nsl.R.id;
import asia.nsgc.nsl.R.layout;

public class StartActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        Button button = (Button) findViewById(R.id.start_button_id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText text = (EditText) findViewById(R.id.price_per_cigarettes_id);
            	
                SharedPreferences preference = getSharedPreferences("userInfo.pref", MODE_PRIVATE);
                Editor editor = preference.edit();
                try {
                	editor.putInt("Price", Integer.parseInt(text.getText().toString()));
                	editor.commit();
                } catch (NumberFormatException e) {
                	e.printStackTrace();
                } finally {
                	Intent intent = new Intent(StartActivity.this, MainActivity.class);
                	startActivity(intent);
                }
            }
        });
    }
}