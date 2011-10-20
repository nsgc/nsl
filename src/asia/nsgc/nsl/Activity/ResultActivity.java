package asia.nsgc.nsl.Activity;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
		int price = intent.getIntExtra("Price", 0);

		Integer dailyLastPrice = selectDailyLastPrice(price);
		insertDailyLastPrice(dailyLastPrice);
		TextView dr = (TextView) findViewById(R.id.daily_result_id);
		dr.setText(dailyLastPrice.toString());

		TextView mr = (TextView) findViewById(R.id.monthly_result_id);
		mr.setText(upsertTableToTotalPrice(price, "MonthlyResult").toString());

		TextView tr = (TextView) findViewById(R.id.total_result_id);
		tr.setText(upsertTableToTotalPrice(price, "TotalResult").toString());

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

	/**
	 * 同月(同日)がある場合はpriceを足して更新、初めての月(初めての日)の場合はpriceを足して登録、初めての利用の場合はpriceで登録.
	 * 
	 * @param price
	 *            我慢・喫煙による金額の変動を渡す
	 * @param targetTable
	 *            月毎の結果テーブル(MonthlyResult) or 総計の結果テーブル(TotalResult)
	 * @return lastPrice これまでの金額総計を返す
	 */
	private Integer upsertTableToTotalPrice(Integer price, String targetTable) {
		Integer lastPrice = 7;
		int substringEnd = 0;
		String selectDateFormat = "yyyy/MM/dd";
		if (targetTable.equals("TotalResult")) {
			substringEnd = 4;
			selectDateFormat = "yyyy/MM";
		}
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getWritableDatabase();

		SimpleDateFormat fmt = new SimpleDateFormat(selectDateFormat);
		String today = fmt.format(new GregorianCalendar().getTime());

		Cursor cursor = db.query(targetTable, new String[] { "total_price", "date" }, "date like ?", new String[] { today.substring(0, substringEnd) + "%" }, null, null, "date DESC", "1");
		ContentValues values = new ContentValues();
		if (cursor.moveToFirst()) {
			if (cursor.getString(cursor.getColumnIndex("date")).equals(today)) {
				lastPrice = cursor.getInt(cursor.getColumnIndex("total_price"))
						+ price;
				values.put("total_price", lastPrice);
				db.update(targetTable, values, "date = ?",
						new String[] { today });
			} else {
				lastPrice = cursor.getInt(cursor.getColumnIndex("total_price"))
						+ price;
				values.put("total_price", lastPrice);
				values.put("date", today);
				db.insert(targetTable, null, values);
			}
		} else {
			lastPrice = price;
			values.put("total_price", lastPrice);
			values.put("date", today);
			db.insert(targetTable, null, values);
		}
		cursor.close();
		db.close();
		helper.close();

		return lastPrice;
	}

	private Integer selectDailyLastPrice(Integer price) {
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getReadableDatabase();

		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		Cursor c = db.query("DailyResult",
				new String[] { "date", "total_price" }, "date like ?",
				new String[] { fmt.format(cal.getTime()) + "%" }, null, null,
				"date DESC", "1");
		Integer lastPriceForTable;

		if (c.moveToFirst()) {
			lastPriceForTable = c.getInt(c.getColumnIndex("total_price"))
					+ price;
		} else {
			// 新しい日で初めて起動した場合、priceの値をセットする
			lastPriceForTable = price;
		}
		c.close();
		db.close();

		return lastPriceForTable;
	}

	private void insertDailyLastPrice(Integer price) {
		MyDBHelper helper = new MyDBHelper(this.getApplicationContext());
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");

		values.put("date", fmt.format(new GregorianCalendar().getTime()));
		values.put("total_price", (Integer) price);
		db.insert("DailyResult", "", values);

		db.close();
		helper.close();

	}
}
