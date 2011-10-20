package asia.nsgc.nsl.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import asia.nsgc.nsl.R;

public class MainActivity extends BaseActivity implements OnClickListener {
	/** 1本あたりの煙草の値段. */
	private Integer mPrice;
	/** 禁煙ボタン. */ 
	private Button mGiveUpSmokingButton;
	/** 喫煙ボタン. */ 
	private Button mSmokingButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences preference = getSharedPreferences("userInfo.pref", MODE_PRIVATE);
		mPrice = preference.getInt("Price", 0);

		if (mPrice == 0) {
			Intent intent = new Intent(MainActivity.this, StartActivity.class);
			startActivity(intent);
		} else {
			setContentView(R.layout.main);

			mGiveUpSmokingButton = (Button) findViewById(R.id.give_up_smoking_button_id);
			mGiveUpSmokingButton.setOnClickListener(this);

			mSmokingButton = (Button) findViewById(R.id.smoking_button_id);
			mSmokingButton.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder dlg = new AlertDialog.Builder(this);

		final Intent intent = new Intent(MainActivity.this, ResultActivity.class);

		if (v == mGiveUpSmokingButton) {
			dlg.setTitle("Nice!!")
					.setMessage("お金が" + mPrice + "円増えた!!")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									intent.putExtra("Price", mPrice);
									startActivity(intent);
								}
							}).show();
		} else if (v == mSmokingButton) {
			dlg.setTitle("Boo!!")
					.setMessage("お金が" + mPrice + "円減った!!")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									intent.putExtra("Price", -mPrice);
									startActivity(intent);
								}
							}).show();
		}
	}
}
