package asia.nsgc.nsl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context) {
        super(context, "NSLDatabase", null, 1);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table DailyResult(" +
                "   date text not null," +
                "   total_price integer not null" +
                ");"
            );
        db.execSQL(
                "create table MonthlyResult(" +
                "   date text not null," +
                "   total_price integer not null" +
                ");"
            );
        db.execSQL(
                "create table TotalResult(" +
                "   date text not null," +
                "   total_price integer not null" +
                ");"
            );        
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
