package asia.nsgc.nsl;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends Activity {
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_result_id) {
			// TODO: GOTO ResultActivity
		} else {
			Intent intent = new Intent(this, ChartActivity.class);
			intent.putExtra("type", item.getItemId());
			startActivity(intent);			
		}

		return true;
	}
}
