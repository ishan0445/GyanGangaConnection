package com.ishan0445.gyangangaconnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginAndSignup extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginandsignup);

		Button login = (Button) findViewById(R.id.login);
		Button signup = (Button) findViewById(R.id.signup);
		TextView tx = (TextView) findViewById(R.id.text);

		Typeface type2 = Typeface.createFromAsset(getAssets(),
				"fonts/roboto.ttf");

		login.setTypeface(type2);
		signup.setTypeface(type2);
		tx.setTypeface(type2);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent lIn = new Intent(LoginAndSignup.this, Login.class);
				startActivity(lIn);
			}
		});

		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent sIN = new Intent(LoginAndSignup.this, Signup.class);
				startActivity(sIN);

			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.menu2, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.ExitMenu:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		}
		return false;
	}
}
