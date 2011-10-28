package asia.nsgc.nsl.Activity;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.afree.data.category.DefaultCategoryDataset;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import asia.nsgc.nsl.MyDBHelper;
import asia.nsgc.nsl.R;
import asia.nsgc.nsl.View.ChartView;

public class ChartActivity extends BaseActivity {
	public static final int DEFAULT_ID = 0;
	public int type;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getIntent().getIntExtra("type", DEFAULT_ID);
		
		ChartQuery query = new ChartQuery();
		setChartQuery(query, type);
		
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor c = db.query(query.table, query.columns, query.selection, query.selectionArgs, query.groupBy, query.having, query.orderBy);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (c.moveToFirst()) {
			int count = c.getCount();
			for (int i = 0; i < count; i++) {
				dataset.addValue(c.getInt(c.getColumnIndex("total_price")), "お金", c.getString(c.getColumnIndex("date")).substring(query.substringIndex));
				Log.d("ChartActivity", c.getString(c.getColumnIndex("date")));
				c.moveToNext();
			}
		}
		c.close();
		db.close();
		helper.close();
	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new ChartView(this, dataset, query.title));
	}
	
	private void setChartQuery(ChartQuery query, int type){
		GregorianCalendar cal = new GregorianCalendar();
		String today = "";
		
		switch (type) {
		case R.id.menu_daily_chart_id:
			today = new SimpleDateFormat("yyyy/MM/dd").format(cal.getTime());
			query.title = today + "のランキング";
			query.substringIndex = 10;
			
			query.table = "DailyResult";
			query.selection = "date like ?";
			query.selectionArgs = new String[] { today + "%"};
			break;
		case R.id.menu_monthly_chart_id:
			today = new SimpleDateFormat("yyyy/MM").format(cal.getTime());
			query.title = today + "のランキング";
			query.substringIndex = 5;
			
			query.table = "MonthlyResult";
			query.selection = "date like ?";
			query.selectionArgs = new String[] { today + "%"};
			break;
		case R.id.menu_total_chart_id:
			today = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z").format(cal.getTime());
			query.title = "総合ランキング(" + today + "時点)";

			query.table = "TotalResult";
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());
		float actionDownX = 0;

		
		switch ( event.getAction() ) {
		case MotionEvent.ACTION_DOWN:
			actionDownX = event.getX();
			Log.d("ChartActivity", "ACTION_DOWN");
			break;

		case MotionEvent.ACTION_MOVE:
			float currentX = event.getX();			
            if (actionDownX < currentX) {
        	
            }
            if (actionDownX > currentX) {

            }
			Log.d("ChartActivity", "ACTION_MOVE");
			break;

		case MotionEvent.ACTION_UP:
			Log.d("ChartActivity", "ACTION_UP");
			break;

		case MotionEvent.ACTION_CANCEL:
			Log.d("ChartActivity", "ACTION_CANCEL");
			break;
	    	}

		return super.onTouchEvent(event); 
	}
}
