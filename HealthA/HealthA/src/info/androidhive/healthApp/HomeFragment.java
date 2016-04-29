package info.androidhive.healthApp;

import info.androidhive.healthApp.R;
import SQLiteDataBase.DatabaseOperations;
import alarm.AlarmManagerBroadcastReceiver;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	private TextView textView1;
	private EditText editText1;
	private AlarmManagerBroadcastReceiver alarm;
	Button sumbit_button;
	String tmp;
	String[] test_date={"12-11","13-12","14-12","15-12","16-12"};
	String[] count ={ "0","1","2","3","8"};
	String[] BMI = {"1","2","3","4","5"};
	String[] RACE = {"2","3","4","5","6"};
	String[] weight={"110","111","112","113","114"};
	String[] height={"115","116","117","118","119"};
	//Context context = getActivity();
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        String data_name;
        textView1= (TextView) rootView.findViewById(R.id.txtName);
        editText1=(EditText) rootView.findViewById(R.id.editName);
        sumbit_button = (Button)rootView.findViewById(R.id.buttonName);
        DatabaseOperations DB = new DatabaseOperations(getActivity());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!prefs.getBoolean("name", false)) {
        	textView1.setText("Please Enter your name:");
        	sumbit_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DatabaseOperations DB = new DatabaseOperations(getActivity());
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
					if(editText1.getText()!=null||editText1.getText().equals(""))
					{
						tmp=editText1.getText().toString();
						for(int i=0;i<5;i++)
			            {
			            	DB.putInformation(DB,tmp,test_date[i],count[i],weight[i],height[i],BMI[i],RACE[i]);
			            }
						SharedPreferences.Editor editor = prefs.edit();
			            editor.putBoolean("name", true);
			            editor.commit();
			            Context context = getActivity();
						Toast.makeText(context, tmp+", Welcome", Toast.LENGTH_LONG).show();
						editText1.setEnabled(false);
			        	editText1.setGravity(Gravity.CENTER);
			        	editText1.setClickable(false);
			        	editText1.setFocusable(false);
			        	editText1.setFilters(new InputFilter[] { new textChange() });
			        	editText1.setInputType(0);
			        	sumbit_button.setClickable(false);
			        	sumbit_button.setEnabled(false);
			        	textView1.setText("Hello,"+tmp+".");
			        	alarm = new AlarmManagerBroadcastReceiver();
			        	Context context2 = getActivity().getApplicationContext();
			            if(alarm != null)
			            {
			    			alarm.SetAlarm(context2);
			            }
					}
					
				}
			});
        }
        else
        {
        	editText1.setEnabled(false);
        	editText1.setGravity(Gravity.CENTER);
        	editText1.setClickable(false);
        	editText1.setFocusable(false);
        	editText1.setFilters(new InputFilter[] { new textChange() });
        	editText1.setInputType(0);
        	sumbit_button.setClickable(false);
        	sumbit_button.setEnabled(false);
        	Cursor CR = DB.getInformation(DB);
            CR.moveToFirst();
            do
            {
            	data_name=CR.getString(0);
            }while(CR.moveToNext());
        	textView1.setText("Hello,"+data_name+". Welcome back!");
        }
        return rootView;
    }
}
