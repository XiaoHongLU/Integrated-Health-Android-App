package SQLiteDataBase;

import android.provider.BaseColumns;

public class TableData {
	

	public TableData()
	{
		
	}
	
	public static abstract class TableInfo implements BaseColumns
	{
		public static final String COLUMN_DATE = "Date";
		public static final String COLUMN_COUNT = "Count";
		public static final String COLUMN_BMI = "BMI";
		public static final String COLUMN_WEIGHT = "WEIGHT";
		public static final String COLUMN_HEIGHT = "HEIGHT";
		public static final String COLUMN_HEARTRACE = "HEART_RACE";
		public static final String COLUMN_NAME="NAME";
		public static final String DATABASE_NAME = "Step_Count";
		public static final String TABLE_NAME = "Summary_Data";
	}

}
