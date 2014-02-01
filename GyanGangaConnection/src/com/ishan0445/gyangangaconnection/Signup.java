package com.ishan0445.gyangangaconnection;

import java.util.regex.Pattern;

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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends Activity implements OnClickListener {
	private Button bt;
	private EditText userName, email, rollNo, password, passwordRe, name;
	private TextView tex;
	private ProgressBar mProgressBar;

	private Typeface type;

	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		bt = (Button) findViewById(R.id.btnSignup);
		userName = (EditText) findViewById(R.id.SUsername);
		password = (EditText) findViewById(R.id.SPassword);
		passwordRe = (EditText) findViewById(R.id.SPasswordRe);
		email = (EditText) findViewById(R.id.email);
		name = (EditText) findViewById(R.id.Name);
		rollNo = (EditText) findViewById(R.id.rollNO);
		tex = (TextView) findViewById(R.id.sigupTop);
		mProgressBar = (ProgressBar) findViewById(R.id.progress);

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

		final String user = userName.getText().toString().toLowerCase().trim();
		final String pass = password.getText().toString();
		String passRe = passwordRe.getText().toString();
		final String ema = email.getText().toString().toLowerCase().trim();
		final String nam = name.getText().toString().trim();
		String roll = rollNo.getText().toString().toUpperCase().trim();

		if (checkEmail(ema)) {
			if (pass.equals(passRe)) {
				if (!containsWhiteSpace(nam)) {
					Toast.makeText(getApplicationContext(),
							"UserName cannot contain Spaces",
							Toast.LENGTH_SHORT).show();
				} else {
					mProgressBar.setVisibility(View.VISIBLE);
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
								mProgressBar.setVisibility(View.INVISIBLE);

								Intent in = new Intent(Signup.this,
										FollowedFriendList.class);
								startActivity(in);
							} else {
								if (Integer.parseInt(e.toString()) == ParseException.EMAIL_TAKEN) {
									mProgressBar.setVisibility(View.INVISIBLE);
									Toast.makeText(getApplicationContext(),
											"Email Already Taken",
											Toast.LENGTH_SHORT).show();
								}
							}
						}
					});
				}

			} else {
				Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
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
	
	public static boolean containsWhiteSpace(final String testCode){
	    if(testCode != null){
	        for(int i = 0; i < testCode.length(); i++){
	            if(Character.isWhitespace(testCode.charAt(i))){
	                return true;
	            }
	        }
	    }
	    return false;
	}
}
