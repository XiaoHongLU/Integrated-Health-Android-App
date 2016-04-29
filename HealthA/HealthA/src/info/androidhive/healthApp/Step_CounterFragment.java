package info.androidhive.healthApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.androidhive.healthApp.R;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;

import SQLiteDataBase.DatabaseOperations;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Step_CounterFragment extends Fragment implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mStepCounterSensor;
	private Sensor mStepDetectorSensor;
	private TextView textView;
	public static int value = -1;
	private int check1=0;
	
	public Step_CounterFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_counter, container, false);
        DatabaseOperations DB = new DatabaseOperations(getActivity());
       //Start Update database
        Calendar c = Calendar.getInstance();
    	SimpleDateFormat df = new SimpleDateFormat("dd-MM");
    	String formattedDate = df.format(c.getTime());
        //End Update
        
        //get data from database
        String[] data_date=new String[5];
    	GraphViewData[] data_count = new GraphViewData[5];
    	double value;
    	int n=0;
        Cursor CR = DB.getInformation(DB);
        CR.moveToFirst();
        do
        {
        	data_date[n]=CR.getString(1); //get the date
        	value=CR.getDouble(2);
        	data_count[n]=new GraphViewData(n+1,value); //get the count
        	if(n==4)
        	{
        		data_date[0]=data_date[1];
        		data_date[1]=data_date[2];
        		data_date[2]=data_date[3];
        		data_date[3]=data_date[4];
        		data_count[0]=data_count[1];
        		data_count[1]=data_count[2];
        		data_count[2]=data_count[3];
        		data_count[3]=data_count[4];
        	}
        	else
        	{
        		n++;
        	}
        	
        }while(CR.moveToNext());
        //End get database
        
        textView=(TextView)rootView.findViewById(R.id.stepcount_result);
       mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
      mStepCounterSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
      mStepDetectorSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        GraphViewSeries sampleGraph = new GraphViewSeries(data_count);
        GraphView graphView = new BarGraphView(getActivity(),"");
        graphView.setScrollable(true);
        graphView.setViewPort(0, 5);
        graphView.setHorizontalLabels(data_date);
        //graphView.setVerticalLabels(new String[]{"2","4","6","8","10"});
        graphView.addSeries(sampleGraph);
        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.graph);
        layout.addView(graphView);
        return rootView;
	}
	
	
	public void onSensorChanged(SensorEvent event)
	{
		Sensor sensor = event.sensor;
		float[] values =event.values;
		
		if(values.length>0)
		{
			value=(int)values[0];
		}
		if(sensor.getType()==Sensor.TYPE_STEP_COUNTER)
		{
			textView.setText("Step Counter Detected: "+value);
		}else if(sensor.getType()==Sensor.TYPE_STEP_DETECTOR)
		{
			textView.setText("Step Detector Detected :"+value);
		}
	}
	public void onResume()
	{
		super.onResume();
		mSensorManager.registerListener(this,mStepCounterSensor,SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(this, mStepDetectorSensor,SensorManager.SENSOR_DELAY_FASTEST);
	}
	public void onStop()
	{
		super.onStop();
		mSensorManager.unregisterListener(this,mStepCounterSensor);
		mSensorManager.unregisterListener(this,mStepDetectorSensor);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
}
