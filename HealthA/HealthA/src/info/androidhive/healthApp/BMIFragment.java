package info.androidhive.healthApp;

import info.androidhive.healthApp.R;

import java.text.DecimalFormat;

import SQLiteDataBase.DatabaseOperations;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMIFragment extends Fragment {
	DecimalFormat df2 = new DecimalFormat("00.00");
	private EditText editText1, editText2;
	private TextView resultText;
	Button sumbit_button;
	String data_date,data_step,data_heart,data_name;
	String tmp1,tmp2;

	public BMIFragment() {

	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);
        
    	editText1=(EditText)rootView.findViewById(R.id.weight_input);
    	editText2=(EditText)rootView.findViewById(R.id.height_input);
    	resultText=(TextView)rootView.findViewById(R.id.bmi_output);
    	sumbit_button = (Button)rootView.findViewById(R.id.showcase);
    
    	DatabaseOperations DB = new DatabaseOperations(getActivity());
        Cursor CR = DB.getInformation(DB);
        CR.moveToFirst();
        do
        {
        	data_date=CR.getString(1);
        	data_name=CR.getString(0);
        	data_step=CR.getString(2);
        	data_heart=CR.getString(6);
        }while(CR.moveToNext());
        
    	sumbit_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseOperations DB = new DatabaseOperations(getActivity());
				String result;
				if(editText1.getText()!=null || editText2.getText()!=null)
				{
					result=String.valueOf(df2.format(Double.parseDouble(editText1.getText().toString())/(Double.parseDouble(editText2.getText().toString())*Double.parseDouble(editText2.getText().toString()))));
					resultText.setText(result);				
					tmp1=String.valueOf(editText1.getText());
					tmp2=String.valueOf(editText2.getText());
					DB.putInformation(DB,data_name,data_date,data_step,tmp1,tmp2,result,data_heart);
				}
			}
		});
        return rootView;
    }

	private void calculateResult() throws NumberFormatException {
		Editable editableValue1 = editText1.getText(), editableValue2 = editText2
				.getText();
		double value1 = 0.0, value2 = 0.0, result;
		if (editableValue1 != null) {
			value1 = Double.parseDouble(editableValue1.toString());
		}
		if (editableValue2 != null) {
			value2 = Double.parseDouble(editableValue2.toString());
		}

		result = value1 / (value2 * value2);
		resultText.setText(String.valueOf(result));
	}
}
