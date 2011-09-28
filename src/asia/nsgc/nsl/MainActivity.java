package asia.nsgc.nsl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnClickListener {
	private Integer price;
	private Button giveUpSmokingButton;
	private Button smokingButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences preference = getSharedPreferences("userInfo.pref",
				MODE_PRIVATE);
		price = preference.getInt("Price", 0);

		if (price == 0) {
			Intent intent = new Intent(MainActivity.this, StartActivity.class);
			startActivity(intent);
		} else {
			setContentView(R.layout.main);

			giveUpSmokingButton = (Button) findViewById(R.id.give_up_smoking_button_id);
			giveUpSmokingButton.setOnClickListener(this);

			smokingButton = (Button) findViewById(R.id.smoking_button_id);
			smokingButton.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder dlg;
		dlg = new AlertDialog.Builder(this);

		final Intent intent = new Intent(MainActivity.this, ResultActivity.class);

		if (v == giveUpSmokingButton) {
			dlg.setTitle("Nice!!")
					.setMessage("お金が" + price + "円増えた!!")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									intent.putExtra("Price", price);
									startActivity(intent);
								}
							}).show();
		} else if (v == smokingButton) {
			dlg.setTitle("Boo!!")
					.setMessage("お金が" + price + "円減った!!")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									intent.putExtra("Price", -price);
									startActivity(intent);
								}
							}).show();
		}
	}
}
