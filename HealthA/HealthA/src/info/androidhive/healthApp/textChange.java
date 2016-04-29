package info.androidhive.healthApp;

import android.text.InputFilter;
import android.text.Spanned;

public class textChange implements InputFilter {

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
            Spanned dest, int dstart, int dend) {

        return dest.subSequence(dstart, dend);
    }

}
