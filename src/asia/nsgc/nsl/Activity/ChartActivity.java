package asia.nsgc.nsl.Activity;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.afree.data.category.DefaultCategoryDataset;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import asia.nsgc.nsl.MyDBHelper;
import asia.nsgc.nsl.R;
import asia.nsgc.nsl.View.ChartView;

public class ChartActivity extends BaseActivity {
	public static final int DEFAULT_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getReadableDatabase();

		String title = "";

		Intent intent = getIntent();
		int type = intent.getIntExtra("type", DEFAULT_ID);
		GregorianCalendar cal = new GregorianCalendar();
		Cursor c = null;

		String today = "";
		String table = "";
		String selection = null;
		String[] selectionArgs = null;
		int substringIndex = 0;
		SimpleDateFormat fmt = null;
		
		switch (type) {
		case R.id.menu_daily_chart_id:
			fmt = new SimpleDateFormat("yyyy/MM/dd");
			today = fmt.format(cal.getTime());
			title = today + "のランキング";
			
			table = "DailyResult";
			selection = "date like ?";
			selectionArgs = new String[] { today + "%"};
			substringIndex = 10;
			break;
		case R.id.menu_monthly_chart_id:
			fmt = new SimpleDateFormat("yyyy/MM");
			today = fmt.format(cal.getTime());
			title = today + "のランキング";
			
			table = "MonthlyResult";
			selection = "date like ?";
			selectionArgs = new String[] { today + "%"};
			substringIndex = 5;
			break;
		case R.id.menu_total_chart_id:
			fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
			today = fmt.format(cal.getTime());
			title = "総合ランキング(" + today + "時点)";

			table = "TotalResult";
			break;
		default:
			break;
		}
		c = db.query(table, new String[] { "date", "total_price" }, selection, selectionArgs, null, null, "date ASC");
		
		if (c.moveToFirst()) {
			int count = c.getCount();
			for (int i = 0; i < count; i++) {
				dataset.addValue(c.getInt(c.getColumnIndex("total_price")), "お金", c.getString(c.getColumnIndex("date")).substring(substringIndex));
				Log.d("ChartActivity", c.getString(c.getColumnIndex("date")));
				c.moveToNext();
			}
		}
		c.close();
		db.close();

		ChartView cv = new ChartView(this, dataset, title);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(cv);
	}
}
