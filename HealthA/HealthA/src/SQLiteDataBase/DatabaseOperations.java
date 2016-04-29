package SQLiteDataBase;

import SQLiteDataBase.TableData.TableInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOperations extends SQLiteOpenHelper {
	public static final int  database_version = 1;
	public String CREATE_QUERY = "CREATE TABLE "+TableInfo.TABLE_NAME+"("+TableInfo.COLUMN_NAME+" TEXT,"+TableInfo.COLUMN_DATE+" TEXT,"+TableInfo.COLUMN_COUNT+" REAL,"+TableInfo.COLUMN_WEIGHT+" REAL,"+TableInfo.COLUMN_HEIGHT+" REAL,"+TableInfo.COLUMN_BMI+" REAL,"+TableInfo.COLUMN_HEARTRACE+" REAL);";

	public DatabaseOperations(Context context) {
		super(context,TableInfo.DATABASE_NAME, null, database_version);
		Log.d("Database operations","Database created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_QUERY);
		Log.d("Database operations","Table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	public void putInformation(DatabaseOperations dop,String name,String date, String count,String weight, String height, String BMI, String RACE)
	{
		SQLiteDatabase SQ = dop.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TableInfo.COLUMN_NAME, name);
		cv.put(TableInfo.COLUMN_DATE, date);
		cv.put(TableInfo.COLUMN_COUNT, count);
		cv.put(TableInfo.COLUMN_WEIGHT,weight);
		cv.put(TableInfo.COLUMN_HEIGHT, height);
		cv.put(TableInfo.COLUMN_BMI,BMI);
		cv.put(TableInfo.COLUMN_HEARTRACE, RACE);
		long k = SQ.insert(TableInfo.TABLE_NAME, null, cv);
		Log.d("Database operations","One raw inserted");
	}
	
	public Cursor getInformation(DatabaseOperations dop)
	{
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String[] coloumns = {TableInfo.COLUMN_NAME,TableInfo.COLUMN_DATE,TableInfo.COLUMN_COUNT,TableInfo.COLUMN_WEIGHT,TableInfo.COLUMN_HEIGHT,TableInfo.COLUMN_BMI,TableInfo.COLUMN_HEARTRACE};
		Cursor CR =SQ.query(TableInfo.TABLE_NAME, coloumns, null, null, null, null, null);
		return CR;
	}

}
