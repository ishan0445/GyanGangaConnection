package com.ishan0445.gyangangaconnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends Activity implements OnClickListener {
	private EditText password, userName;
	private Button bt;
	private TextView tex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		userName = (EditText) findViewById(R.id.LUsername);
		password = (EditText) findViewById(R.id.LPassword);
		bt = (Button) findViewById(R.id.btnLogin);
		bt.setOnClickListener(this);
		tex = (TextView) findViewById(R.id.loginTop);

		Typeface type2 = Typeface.createFromAsset(getAssets(),
				"fonts/roboto.ttf");

		tex.setTypeface(type2);
		bt.setTypeface(type2);
		userName.setTypeface(type2);
		password.setTypeface(type2);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ParseUser.logInInBackground(userName.getText().toString(), password
				.getText().toString(), new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// Hooray! The user is logged in.
					Intent in = new Intent(Login.this,FriendsList.class);
					startActivity(in);
				} else {
					// Signup failed. Look at the ParseException to see what
					// happened.
				}
			}
		});
	}

}
