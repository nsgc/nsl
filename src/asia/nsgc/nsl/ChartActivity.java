package asia.nsgc.nsl;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.afree.data.category.DefaultCategoryDataset;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class ChartActivity extends BaseActivity {
	public final static int DEFAULT_ID = 0;

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
		int substring_index = 0;
		String table = "";
		String selection = null;
		String selectionArgs[] = null;
		
		switch (type) {
		case R.id.menu_daily_chart_id:
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			String today = fmt.format(cal.getTime());
			
			table = "DailyResult";
			selection = "date like ?";
			selectionArgs = new String[] { today + "%"};
			substring_index = 10;
			title = today + "のランキング";
			break;
		case R.id.menu_monthly_chart_id:
			SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy/MM");
			String today2 = fmt2.format(cal.getTime());
			
			table = "MonthlyResult";
			selection = "date like ?";
			selectionArgs = new String[] { today2 + "%"};
			substring_index = 5;
			title = today2 + "のランキング";
			break;
		case R.id.menu_total_chart_id:
			SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
			String today3 = fmt3.format(cal.getTime());
			table = "TotalResult";
			title = "総合ランキング(" + today3 + "時点)";
			break;
		default:
			break;
		}
		c = db.query(table,
				new String[] { "date", "total_price" }, selection,
				selectionArgs, null, null, "date ASC");
		
		if (c.moveToFirst()) {
			int count = c.getCount();
			for (int i = 0; i < count; i++) {
				dataset.addValue(c.getInt(1), "お金", c.getString(0).substring(substring_index));
				Log.d("ChartActivity", c.getString(0));
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
