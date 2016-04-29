package info.androidhive.healthApp;



import java.util.Calendar;

import info.androidhive.healthApp.R;

import com.github.amlcurran.showcaseview.ApiUtils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import SQLiteDataBase.DatabaseOperations;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Personal_InfoFragment extends Fragment implements View.OnClickListener {
	private ShowcaseView showcaseView;
	private int counter =0;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	TextView text_weight,text_height,text_bmi,text_race,text_count,text_name;
	private final ApiUtils apiUtils = new ApiUtils();
	Context context=getActivity();
	
	public Personal_InfoFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        textView1 = (TextView) rootView.findViewById(R.id.name_info);
        textView2 = (TextView) rootView.findViewById(R.id.height_info);
        textView3 = (TextView) rootView.findViewById(R.id.step_info);
        text_name=(TextView) rootView.findViewById(R.id.name_data);
        text_weight = (TextView) rootView.findViewById(R.id.weight_data);
        text_height = (TextView) rootView.findViewById(R.id.height_data);
        text_bmi = (TextView) rootView.findViewById(R.id.bmi_data);
        text_race = (TextView) rootView.findViewById(R.id.heart_data);
        text_count = (TextView) rootView.findViewById(R.id.step_data);
        
        
        if(!prefs.getBoolean("firstTime", false)) {
        showcaseView = new ShowcaseView.Builder(getActivity())
        .setTarget(new ViewTarget(rootView.findViewById(R.id.name_info)))
        .setOnClickListener(this)
        .build();
        showcaseView.setButtonText(getString(R.string.next));
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstTime", true);
        editor.commit();
        }
        
        DatabaseOperations DB = new DatabaseOperations(getActivity());
        String data_weight,data_height,data_bmi,data_heart,data_step,data_name;
    	int n=0;
        Cursor CR = DB.getInformation(DB);
        CR.moveToFirst();
        do
        {
        	data_name=CR.getString(0);
        	data_step=CR.getString(2);
        	data_weight=CR.getString(3); //get the WEIGHT string
        	data_height=CR.getString(4);
        	data_bmi=CR.getString(5);
        	data_heart=CR.getString(6);
        	n++;
        }while(CR.moveToNext());
       text_name.setText(data_name);
       text_weight.setText(data_weight);
       text_height.setText(data_height);
       text_bmi.setText(data_bmi);
       text_race.setText(data_heart);
       text_count.setText(data_step);
         
        return rootView;
    }

	private void setAlpha(float alpha, View...views )
	{
		if(apiUtils.isCompatWithHoneycomb())
		{
			for(View view : views)
			{
				view.setAlpha(alpha);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (counter) {
        case 0:
            showcaseView.setShowcase(new ViewTarget(textView2), true);
            break;

        case 1:
            showcaseView.setShowcase(new ViewTarget(textView3), true);
            break;

        case 2:
            showcaseView.setTarget(Target.NONE);
            showcaseView.setContentTitle("Check it out");
            showcaseView.setContentText("You don't always need a target to showcase");
            showcaseView.setButtonText(getString(R.string.close));
            setAlpha(0.4f, textView1, textView2, textView3);
            break;

        case 3:
            showcaseView.hide();
            setAlpha(1.0f, textView1, textView2, textView3);
            break;
    }
    counter++;
}
}
