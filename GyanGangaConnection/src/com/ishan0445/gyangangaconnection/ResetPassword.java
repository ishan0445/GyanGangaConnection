package com.ishan0445.gyangangaconnection;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ResetPassword extends Activity implements OnClickListener {
	private TextView tv;
	private EditText et;
	private Button bt;
	private ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reset_password);

		tv = (TextView) findViewById(R.id.ResetTop);
		et = (EditText) findViewById(R.id.resetEmain);
		bt = (Button) findViewById(R.id.resetButton);
		pb = (ProgressBar) findViewById(R.id.progress);
		bt.setOnClickListener(this);

		Typeface type = Typeface.createFromAsset(getAssets(),
				"fonts/roboto.ttf");

		tv.setTypeface(type);
		bt.setTypeface(type);
		et.setTypeface(type);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		pb.setVisibility(View.VISIBLE);
		ParseUser.requestPasswordResetInBackground(et.getText().toString(),
				new RequestPasswordResetCallback() {
					public void done(ParseException e) {
						if (e == null) {
							pb.setVisibility(View.INVISIBLE);
							Toast.makeText(
									getApplicationContext(),
									"An email was successfully sent with reset instructions.",
									Toast.LENGTH_SHORT).show();
						} else {
							pb.setVisibility(View.INVISIBLE);
							Toast.makeText(
									getApplicationContext(),
									e.toString(),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}
