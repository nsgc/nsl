package asia.nsgc.nsl.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import asia.nsgc.nsl.MyDBHelper;
import asia.nsgc.nsl.R;

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
		if (item.getItemId() == R.id.debug_mode) {
			final CharSequence[] items = {"テストデータ投入", "データクリア"};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Debug Mode");
			MyDBHelper helper = new MyDBHelper(this);
			final SQLiteDatabase db = helper.getWritableDatabase();
			
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {

			    	switch (item) {
			    	case 0:	
				    	db.delete("TotalResult",   null, null);
				    	db.delete("MonthlyResult", null, null);
				    	db.delete("DailyResult",   null, null);
				    	
						ContentValues values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/06/06 9:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/06/10 7:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", -40);
						values.put("date", "2011/06/10 15:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 20);
						values.put("date", "2011/08/08 10:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 40);
						values.put("date", "2011/08/08 12:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/08/09 10:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 0);
						values.put("date", "2011/08/09 13:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 20);
						values.put("date", "2011/09/10 08:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 40);
						values.put("date", "2011/09/10 09:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 60);
						values.put("date", "2011/09/10 10:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/09/29 13:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 0);
						values.put("date", "2011/09/29 19:00");
				    	db.insert("DailyResult",   null, values);
				    	values = new ContentValues();
						values.put("total_price", 20);
						values.put("date", "2011/09/30 09:00");
				    	db.insert("DailyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 20);
						values.put("date", "2011/10/01 09:00");
				    	db.insert("DailyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 40);
						values.put("date", "2011/10/01 10:00");
				    	db.insert("DailyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/10/11 09:00");
				    	db.insert("DailyResult",   null, values);
				    	
						values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/06/06");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", -60);
						values.put("date", "2011/06/10");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 40);
						values.put("date", "2011/08/08");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 40);
						values.put("date", "2011/08/09");
				    	db.insert("MonthlyResult",   null, values);
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 60);
						values.put("date", "2011/09/10");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 60);
						values.put("date", "2011/09/29");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 80);
						values.put("date", "2011/09/30");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 40);
						values.put("date", "2011/10/01");
				    	db.insert("MonthlyResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 20);
						values.put("date", "2011/10/11");
				    	db.insert("MonthlyResult",   null, values);
				    	
						values = new ContentValues();
						values.put("total_price", -60);
						values.put("date", "2011/06");
				    	db.insert("TotalResult",   null, values);
						values = new ContentValues();
						values.put("total_price", -20);
						values.put("date", "2011/08");
				    	db.insert("TotalResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 60);
						values.put("date", "2011/09");
				    	db.insert("TotalResult",   null, values);
						values = new ContentValues();
						values.put("total_price", 80);
						values.put("date", "2011/10");
				    	db.insert("TotalResult",   null, values);
				    	Log.d("BaseActivity", "insert new data");
				    	break;

			    	case 1:
				    	db.delete("TotalResult",   null, null);
				    	db.delete("MonthlyResult", null, null);
				    	db.delete("DailyResult",   null, null);
				    	Log.d("BaseActivity", "delete data");
				    	break;
			    	}
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			Intent intent = new Intent(this, ChartActivity.class);
			intent.putExtra("type", item.getItemId());
			startActivity(intent);			
		}

		return true;
	}
}
