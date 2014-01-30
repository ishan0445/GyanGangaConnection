package com.ishan0445.gyangangaconnection;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.quickblox.core.QBSettings;

public class Signup extends Activity implements OnClickListener {
	private Button bt;
	private EditText userName, email, rollNo, password, passwordRe, name;
	private TextView tex;

	private Typeface type;
	private ProgressDialog progressDialog;

	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		QBSettings.getInstance().fastConfigInit("6568", "yuNFJRaYP56WWWO",
				"v7NP2NT3JTzTFBe");

		bt = (Button) findViewById(R.id.btnSignup);
		userName = (EditText) findViewById(R.id.SUsername);
		password = (EditText) findViewById(R.id.SPassword);
		passwordRe = (EditText) findViewById(R.id.SPasswordRe);
		email = (EditText) findViewById(R.id.email);
		name = (EditText) findViewById(R.id.Name);
		rollNo = (EditText) findViewById(R.id.rollNO);
		tex = (TextView) findViewById(R.id.sigupTop);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading");

		type = Typeface.createFromAsset(getAssets(), "fonts/roboto.ttf");

		tex.setTypeface(type);
		bt.setTypeface(type);
		userName.setTypeface(type);
		password.setTypeface(type);
		passwordRe.setTypeface(type);
		email.setTypeface(type);
		rollNo.setTypeface(type);
		name.setTypeface(type);

		bt.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		setResult(RESULT_CANCELED, i);
		finish();
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		progressDialog.show();
		final String user = userName.getText().toString();
		final String pass = password.getText().toString();
		String passRe = passwordRe.getText().toString();
		final String ema = email.getText().toString();
		final String nam = name.getText().toString();
		String roll = rollNo.getText().toString();
		progressDialog.show();
		if (checkEmail(ema)) {
			if (pass.equals(passRe)) {
				ParseUser parseUser = new ParseUser();
				parseUser.setUsername(user);
				parseUser.setPassword(pass);
				parseUser.setEmail(ema);

				parseUser.put("RollNo", roll);
				parseUser.put("Name", nam);

				parseUser.signUpInBackground(new SignUpCallback() {

					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							// Hooray! Let them use the app now.
							Intent in = new Intent(Signup.this,
									FriendsList.class);
							startActivity(in);
						} else {
							// Sign up didn't succeed. Look at the
							// ParseException
							// to figure out what went wrong
						}
					}
				});

			} else {
				Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
		}
		progressDialog.hide();
	}
}
