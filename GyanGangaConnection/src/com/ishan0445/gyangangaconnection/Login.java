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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends Activity implements OnClickListener {
	private EditText password, userName;
	private Button bt, reset;
	private TextView tex;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		userName = (EditText) findViewById(R.id.LUsername);
		password = (EditText) findViewById(R.id.LPassword);
		bt = (Button) findViewById(R.id.btnLogin);
		reset = (Button) findViewById(R.id.resetButton);
		bt.setOnClickListener(this);
		reset.setOnClickListener(this);
		tex = (TextView) findViewById(R.id.loginTop);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);

		Typeface type2 = Typeface.createFromAsset(getAssets(),
				"fonts/roboto.ttf");

		tex.setTypeface(type2);
		bt.setTypeface(type2);
		userName.setTypeface(type2);
		password.setTypeface(type2);
		reset.setTypeface(type2);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
			mProgressBar.setVisibility(View.VISIBLE);
			String user = userName.getText().toString();
			String pass = password.getText().toString();

			if (user.equals("") && pass.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Username or Password cannot be left blank",
						Toast.LENGTH_SHORT).show();
			} else {
				ParseUser.logInInBackground(user.toLowerCase(), pass,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// Hooray! The user is logged in.
									mProgressBar.setVisibility(View.INVISIBLE);
									Intent in = new Intent(Login.this,
											FollowedFriendList.class);
									startActivity(in);
								} else {
									// Signup failed. Look at the ParseException
									// to see
									// what
									// happened.
								}
							}
						});
			}

			break;

		case R.id.resetButton:
			Intent i = new Intent(Login.this, ResetPassword.class);
			startActivity(i);
			break;
		}

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent main = new Intent(Login.this, LoginAndSignup.class);
		finish();
		startActivity(main);
	}

}
