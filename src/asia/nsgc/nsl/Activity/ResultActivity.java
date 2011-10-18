package asia.nsgc.nsl.Activity;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import asia.nsgc.nsl.MyDBHelper;
import asia.nsgc.nsl.R;
import asia.nsgc.nsl.R.id;
import asia.nsgc.nsl.R.layout;

public class ResultActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		Intent intent = getIntent();
		Integer dailyLastPrice = selectDailyLastPrice(intent.getIntExtra("Price", 0));
		
		insertDailyLastPrice(dailyLastPrice);

		TextView dr = (TextView) findViewById(R.id.daily_result_id);
		dr.setText(dailyLastPrice.toString());

		Integer monthlyLastPrice = insertAndSelectMonthlyLastPrice(dailyLastPrice);

		TextView mr = (TextView) findViewById(R.id.monthly_result_id);
		mr.setText(monthlyLastPrice.toString());

		Integer totalLastPrice = insertAndSelectTotalLastPrice(monthlyLastPrice);

		TextView tr = (TextView) findViewById(R.id.total_result_id);
		tr.setText(totalLastPrice.toString());

		Button button = (Button) findViewById(R.id.to_main_button_id);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ResultActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private Integer insertAndSelectTotalLastPrice(Integer monthlyLastPrice) {
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM");
		String today = fmt.format(cal.getTime());

		Cursor mc = db.query("TotalResult", new String[] { "total_price",
				"date" }, "date = ?", new String[] { today }, null,
				null, "date DESC", "1");
		mc.moveToFirst();

		Integer totalLastPrice = monthlyLastPrice;
		
		if (mc.moveToFirst()) {
			//totalLastPrice = mc.getInt(0) + monthlyLastPrice;
			totalLastPrice = monthlyLastPrice;
			values.put("total_price", totalLastPrice);
			db.update("TotalResult", values, "date = ?",
					new String[] { fmt.format(cal.getTime()) });
		} else {
			totalLastPrice = monthlyLastPrice;
			values.put("total_price", totalLastPrice);
			// 新しい月の場合
			values.put("date", today);
			db.insert("TotalResult", null, values);
		}

		mc.close();
		db.close();
		helper.close();
		return totalLastPrice;
	}

	private Integer insertAndSelectMonthlyLastPrice(Integer dailyLastPrice) {
        MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

        String today = fmt.format(cal.getTime());

        Cursor mc = db.query("MonthlyResult", new String[] { "total_price", "date" }, "date like ?", new String[] {today.substring(0, 7) + "%"}, null, null, "date DESC", "1");

        Integer monthlyLastPrice;
        if (mc.moveToFirst()) {
        	if (mc.getString(mc.getColumnIndex("date")).equals(today)) {
        		monthlyLastPrice = dailyLastPrice;
        		values.put("total_price", monthlyLastPrice);
        		// 同日の場合
        		db.update("MonthlyResult", values, "date = ?",  new String[] {today});
        	} else {
           		monthlyLastPrice = mc.getInt(mc.getColumnIndex("total_price")) + dailyLastPrice;
        		values.put("total_price", monthlyLastPrice);
        		// 新しい日で初めて起動した場合
           		values.put("date", today);
        		db.insert("MonthlyResult", null, values);
        	}
        } else {
          	// 条件：月が変わっている場合は...
            monthlyLastPrice = dailyLastPrice;
      		values.put("total_price", monthlyLastPrice);
       		values.put("date", today);
       		db.insert("MonthlyResult", null, values);
        }

        mc.close();
        db.close();        
        helper.close();
		return monthlyLastPrice;
	}

	private Integer selectDailyLastPrice(Integer price) {
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getReadableDatabase();

		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		Cursor c = db.query("DailyResult", new String[] { "date", "total_price" },
				"date like ?",
				new String[] { fmt.format(cal.getTime()) + "%" }, null, null, "date DESC", "1");
		Integer lastPriceForTable;

		if (c.moveToFirst()) {
			lastPriceForTable = c.getInt(c.getColumnIndex("total_price")) + price;
		} else {
			// 新しい日で初めて起動した場合、priceの値をセットする
			lastPriceForTable = price;
		}
		c.close();
		db.close();

		return lastPriceForTable;
	}

	private void insertDailyLastPrice(Integer dailyLastPrice) {
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");

		values.put("date", fmt.format(cal.getTime()));
		values.put("total_price", (Integer) dailyLastPrice);
		db.insert("DailyResult", "", values);

		db.close();
		helper.close();

	}
}
