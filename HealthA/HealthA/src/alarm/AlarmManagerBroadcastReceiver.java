package alarm;

import info.androidhive.healthApp.R;
import info.androidhive.healthApp.Step_CounterFragment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import SQLiteDataBase.DatabaseOperations;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.DocumentsContract.Root;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	static String data_date,data_heart,data_bmi,data_height,data_weight,data_name,data_step;
	final public static String ONE_TIME = "onetime";
	@Override
	public void onReceive(Context context, Intent intent) {
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();

         //You can do the processing here update the widget/remote views.
         DatabaseOperations DB = new DatabaseOperations(context);
         Cursor CR = DB.getInformation(DB);
         Format formatter = new SimpleDateFormat("MM-dd");
         CR.moveToFirst();
         do
         {
         	data_date=CR.getString(1);
         	data_name=CR.getString(0);
         	data_heart=CR.getString(6);
         	data_bmi=CR.getString(5);
         	data_weight=CR.getString(3);
         	data_height=CR.getString(4);
         }while(CR.moveToNext());
         if(Step_CounterFragment.value==-1)
         {
        	 data_step="5";
         }
         else
         {
        	 data_step=String.valueOf(Step_CounterFragment.value);
        	 Step_CounterFragment.value=0;
         }
         DB.putInformation(DB,data_name,formatter.format(new Date()),data_step,data_weight,data_height,data_bmi,data_heart);
         
         Bundle extras = intent.getExtras();
         StringBuilder msgStr = new StringBuilder();
         
         if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
        	 msgStr.append("Updated at : ");
         }
         msgStr.append(formatter.format(new Date()));

         Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
         //Release the lock
         wl.release();
         
	}
	public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 30 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 *30 , pi); 
    }
}
